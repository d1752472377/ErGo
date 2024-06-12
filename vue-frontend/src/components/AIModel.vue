<template>
    <div class="chat-main">
        <div class="window-header">
                <div class="window-header-title">
                    <div class="name">
                        <div class="window-header-main-title home_chat-body-title__5S8w4">
                            <template v-if="userInfo && userInfo.userName">
    用户：{{ userInfo.userName }}
  </template>
  <template v-else>
    未登录
  </template>
                        </div>
                        <div class="chat-annotation">
                            <div>
                                <div class="c-bubble-trigger com-verification">
                                    <i class="verified"></i>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- 加一个下拉框，选项是 OpenAI 讯飞星火 技术派 -->
            </div>
        <div class="chat-content">
            <div v-for="chatConten in finalChat" class="content">
        
            <div class="my-message-text">我:{{ chatConten.user }}</div>
        
        
            <!-- <p class="my-message-text">我:{{ chatConten }}</p> -->
           
            <div class="gpt-message-text">ChatGPT: {{ chatConten.ai }}</div>
        
    </div>
            <!-- <div v-for="chatConten in finalChat">
            <p style="color:green">我:{{ chatConten.user }}</p>
            <p style="color:red">chatGPT:{{ chatConten.ai }}</p>
        </div> -->
        </div>
        <div class="input">
            <el-row :gutter="20">
        <el-col :span="16">
            <el-input style="margin-left:20px" type="textarea" placeholder="请输入内容" v-model="userInput" maxlength="500"
                show-word-limit :autosize="{ minRows: 2 }" resize="none" />
        </el-col>
        <el-col :span="8">
            <el-button @click="start" type="primary" style="margin-left:50px;width:50%" :loading="loading" >{{ loading ?
                'ai在拼命思考啦' : '提问' }}</el-button>
        </el-col>
    </el-row>


        </div>
    </div>
</template>
   
<script>
import { getUserInfo } from '@/utils/auth';
import CryptoJS from 'crypto-js';

export default {
    data() {
        return {
            appId: 'e0c1f212',
            status: 'init',
            ttsWS: null,
            totalRes: '',
            userInput: '',
            aiContentRequest: '',
            finalChat: [],
            loading: false,
            userInfo:{}
        };
    },
    created(){
        this.userInfo = JSON.parse(getUserInfo());
        console.log(this.userInfo.userName)
    },
    methods: {
        getWebsocketUrl() {
            return new Promise((resolve, reject) => {
                const apiKey = '3554aa0a17812c93448c245fb9a6228d';
                const apiSecret = 'N2NkMjg4M2JlMzBhYTg2MDMyNTllNzlh';
                const url = 'wss://spark-api.xf-yun.com/v1.1/chat'; //这里使用的是星火大模型1.x版本
                const host = window.location.host;
                const date = new Date().toGMTString();
                const algorithm = 'hmac-sha256';
                const headers = 'host date request-line';
                const signatureOrigin = `host: ${host}\ndate: ${date}\nGET /v1.1/chat HTTP/1.1`;
                const signatureSha = CryptoJS.HmacSHA256(signatureOrigin, apiSecret);
                const signature = CryptoJS.enc.Base64.stringify(signatureSha);
                const authorizationOrigin = `api_key="${apiKey}", algorithm="${algorithm}", headers="${headers}", signature="${signature}"`;
                const authorization = window.btoa(authorizationOrigin);
                const finalUrl = `${url}?authorization=${authorization}&date=${date}&host=${host}`;
                console.log(finalUrl)
                resolve(finalUrl);
            });
        },
        setStatus(status) {
            this.status = status;
        },
        connectWebSocket() {
            this.setStatus('ttsing');
            this.getWebsocketUrl().then((url) => {
                let ttsWS;
                if ('WebSocket' in window) {
                    ttsWS = new WebSocket(url);
                } else if ('MozWebSocket' in window) {
                    ttsWS = new MozWebSocket(url);
                } else {
                    alert('浏览器不支持WebSocket');
                    return;
                }
                this.ttsWS = ttsWS;
                ttsWS.onopen = (e) => {
                    this.webSocketSend();
                };
                ttsWS.onmessage = (e) => {
                    this.result(e.data);
                };
                ttsWS.onerror = (e) => {
                    clearTimeout(this.playTimeout);
                    this.setStatus('error');
                    alert('WebSocket报错，请f12查看详情');
                    console.error(`详情查看：${encodeURI(url.replace('wss:', 'https:'))}`);
                };
                ttsWS.onclose = (e) => {
                    console.log(e);
                };
            });
        },
        webSocketSend() {
            let that = this
            const params = {
                header: {
                    app_id: this.appId,
                    uid: '001',
                },
                parameter: {
                    chat: {
                        domain: 'general',//如果是chat2这里也需要进行相应修改
                        temperature: 0.5,
                        max_tokens: 1024,
                    },
                },
                payload: {
                    message: {
                        text: [
                            { "role": "user", "content": that.userInput }
                        ]
                    },
                },
            };
            console.log(JSON.stringify(params));
            this.ttsWS.send(JSON.stringify(params));
        },
        start() {
            this.loading = true
            this.totalRes = '';
            this.aiContentRequest = ''
            this.connectWebSocket();
        },
        requestHandle(requestData) {//处理request
            this.aiContentRequest = this.aiContentRequest + requestData.payload.choices.text[0].content
        },
        result(resultData) {
            let jsonData = JSON.parse(resultData);
            //console.log(jsonData)
            this.totalRes += resultData;
            //this.$refs.outputText.value = this.totalRes;
            //加入到ai回答中
            if (jsonData.header.status !== 2) {//不为结束就进行添加
                this.requestHandle(jsonData)
            } else {
                let contentSomething = {
                    ai: this.aiContentRequest,
                    user: this.userInput
                }
                this.finalChat.push(contentSomething)
                this.userInput = ''
                this.loading = false
            }
            if (jsonData.header.code !== 0) {
                alert(`提问失败: ${jsonData.header.code}:${jsonData.header.message}`);
                console.error(`${jsonData.header.code}:${jsonData.header.message}`);
                return;
            }
            if (jsonData.header.code === 0 && jsonData.header.status === 2) {
                this.ttsWS.close();
                this.setStatus('init');
            }
        },
    },
};
</script>
<style scoped>
.chat-main {
    width: var(--window-content-width);
    padding: 0 20px;
    display: flex;
    flex-direction: column;
    position: relative;
    height: 100%;
    background-color: #fff;
    margin-left: 20px;
}
.window-header {
    padding: 14px 20px;
    border-bottom: 1px solid rgba(0, 0, 0, .1);
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
.window-header-title {
    max-width: calc(100% - 100px);
    overflow: hidden;
}
.window-header-title .name {
    display: flex;
}
.window-header-title .window-header-sub-title {
    font-size: 14px;
    margin-top: 5px;
}
.window-header-title .window-header-main-title {
    font-size: 20px;
    font-weight: bolder;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: block;
    max-width: 50vw;
}
.home_chat-body-title__5S8w4 {
    cursor: pointer;
    color: var(--pai-brand-1-normal);
}
.chat-content {
    width: 1264px;
    height: 350px;
    /* background-color: #DCF8C6; */
    /* overflow: auto;
    flex: 1 1; */
     padding: 20px 20px 40px; 
    position: relative;
    overscroll-behavior: none;
    overflow: auto;

}
.content {
  display: flex;
  flex-direction: column;
  width: 1264px;
  /* background-color: aqua; */
}

.my-message-text {
  align-self: flex-end;
  background-color: #E0E0E0; /* 自定义您希望的背景颜色 */
  padding: 10px;
  border-radius: 10px;
  margin-bottom: 10px; /* 添加一些间距以增加可读性 */
}

.gpt-message-text {
  align-self: flex-start;
  background-color: #E0E0E0; /* 自定义您希望的背景颜色 */
  padding: 10px;
  border-radius: 10px;
  margin-bottom: 10px; /* 添加一些间距以增加可读性 */
}
</style>