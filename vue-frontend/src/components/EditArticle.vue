<template>
    <div>
        <header class="header editor-header">
            <div class="left-box"></div>
            <input placeholder="输入文章标题..." v-model="obj.title" spellcheck="false" maxlength="80"
                class="title-input title-input">
            <div class="right-box">
                <div class="topic-icon"></div>
                <div class="publish-popup publish-popup with-padding">
                    <button class="xitu-btn" @click="showDialog"> 发布 </button>
                    <!-- 弹窗组件 -->
                    <el-dialog title="发布文章" :visible.sync="dialogVisible" width="40%" :before-close="handleClose">
                        <!-- 分类选择 -->
                        <div class="category">
                            <div class="label">分类：</div>
                            <!-- 使用 el-select 组件生成分类选择器 -->
                            <el-select v-model="categoryModel" placeholder="请选择分类" @change="selectedCategory">
                                <!-- 使用 v-for 遍历分类数组，并生成选项 -->
                                <el-option v-for="item in categoryList" :key="item.id" :label="item.categoryName"
                                    :value="item.id">
                                </el-option>
                            </el-select>
                        </div>

                        <!-- 标签输入 -->
                        <div class="tag">
                            <div class="label">标签：</div>
                            <!-- 使用 el-select 组件生成标签选择器 -->
                            <el-select v-model="TagModel" placeholder="请选择标签" @change="selectedTag">
                                <!-- 使用 v-for 遍历标签数组，并生成选项 -->
                                <el-option v-for="item in tageList" :key="item.id" :label="item.tagName" :value="item.id">
                                </el-option>
                            </el-select>

                        </div>

                        <!-- 背景图上传 -->
                        
    
    <div class="upload">
        <div class="label">文章封面：</div>
        <el-upload 
            class="upload-demo" 
            :show-file-list="false"  
            :auto-upload="false"                     
            :action="''" 
            :on-change="handlePictureChange"
        >


            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <svg v-if="showUploadIcon" t="1670660844412" class="upload-icon-up" viewBox="0 0 1029 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="3248" width="24" height="24" style="visibility: visible;">
                    <path d="M661.23 1003.042H119.672c-64.034 0-116.053-51.883-116.053-115.712V115.917C3.618 52.224 55.638 0.068 119.671 0.068H893.27c63.898 0 115.985 52.02 115.985 115.849v539.99c0 21.23-17.34 38.775-38.707 38.775s-38.912-17.34-38.912-38.776v-539.99c0-21.23-17.34-38.638-38.57-38.638H119.67c-21.299 0-38.912 17.408-38.912 38.639v771.14c0 21.231 17.613 38.639 38.912 38.639h541.492c21.162 0 38.707 17.203 38.707 38.639 0.068 21.3-17.545 38.707-38.64 38.707z" fill="#bfbfbf" p-id="3249"></path>
                    <path d="M42.325 771.755c-9.762 0-19.729-4.028-27.238-11.606-14.95-14.95-14.95-39.458 0-54.408l192.785-192.034c35.157-35.158 89.156-44.169 133.803-21.777L551.39 596.65c14.814 7.578 32.768 4.643 44.373-7.167l347.614-346.317c14.95-15.019 39.458-15.019 54.682 0 15.223 14.882 15.087 39.39 0 54.545l-347.75 346.317c-35.09 35.089-88.816 43.759-133.667 21.367L306.86 561.084c-14.95-7.578-32.7-4.506-44.374 7.168L69.7 760.012c-7.51 7.578-17.34 11.743-27.375 11.743zM351.71 385.775c-63.898 0-116.053-51.746-116.053-115.712 0-63.898 51.882-115.712 116.053-115.712 63.76 0 116.053 51.883 116.053 115.712 0 63.898-52.36 115.712-116.053 115.712z m0-154.146c-21.163 0-38.776 17.271-38.776 38.502 0 21.368 17.477 38.64 38.776 38.64 21.163 0 38.639-17.272 38.639-38.64 0-21.23-17.34-38.502-38.64-38.502zM834.833 1024c-21.367 0-38.844-17.203-38.844-38.775V753.869c0-21.095 17.204-38.64 38.844-38.64 21.163 0 38.776 17.34 38.776 38.64v231.356c-0.069 21.572-17.545 38.775-38.776 38.775z" fill="#bfbfbf" p-id="3250"></path>
                    <path d="M989.389 868.284c-9.49 0-18.978-3.345-26.76-10.377l-127.864-120.15-128.478 120.15c-15.36 14.404-39.8 13.858-54.409-1.57-14.677-15.633-13.994-39.868 1.707-54.682L788.89 674.611c11.127-13.721 27.58-21.436 45.533-21.436 17.818 0 34.27 7.988 45.261 21.436l135.441 127.044c15.702 14.814 16.52 38.912 1.775 54.682-6.758 7.782-17.066 11.947-27.511 11.947z" fill="#bfbfbf" p-id="3251"></path>
                  </svg>
        </el-upload>
    </div>


                        <div class="articleType">
                            <div class="label">文章类型：</div>
                            <el-radio v-model="obj.source" label="2" @change="selectRedio">原创</el-radio>
                            <el-radio v-model="obj.source" label="1" @change="selectRedio">转载</el-radio>
                            <el-radio v-model="obj.source" label="3" @change="selectRedio">翻译</el-radio>
                        </div>
                        <div class="articleAdress" v-if="showInput">
                            <el-input placeholder="请输入原文地址" v-model="obj.sourceUrl" >
                                <template slot="prepend">原文地址</template>
                            </el-input>
                        </div>

                        <!-- 文章内容编辑 -->
                        <div class="articleEdit">
                            <div class="label">文章内容：</div>
                            <!-- 这里是富文本编辑器组件，可以根据你的需求来使用 Element UI 中的富文本编辑器或其他组件 -->
                            <textarea v-model="obj.summary" placeholder="请输入文章内容" rows="4" style="height: 80px; width: 400px; resize: none;"></textarea>
                        </div>
                        



                        <div slot="footer" class="dialog-footer">
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button type="primary" @click="handleConfirm">确 定</el-button>
                        </div>
                    </el-dialog>
                </div>
                <nav class="navigator main-navigator with-padding">
                    <el-avatar :src="photo"></el-avatar>
                </nav>
            </div>
        </header>
        <div class="md">
            <MdEditor :content.sync="obj.content" />
        </div>
    </div>
</template>

<script>
import { getUserInfo } from '@/utils/auth';
import { getCategoryList,upload,save } from '../api/blog';
import { getAllTagList } from '@/api/tag'

export default {
    data() {
        return {
            obj: {
                userId: '',
                articleType: 1,
                title: '',
                picture: '',
                content: '',
                summary: '',
                categoryId: '',
                source: '2',
                sourceUrl: '',
                picture:'',
                status:'',
            },
            dialogVisible: false,
            input: '',
            // summary:'',
            photo: '',
            // userId: '',
            articleType: 1,//文章类型
            // title: '',+
            categoryModel: null,
            TagModel: null,
            categoryList: [],
            tageList: [],
            showInput: false,
            showUploadIcon: true,
            imageUrl:'',

        }
    },
    created() {
        this.getTagList()
        this.getCategory()
        const userinfo = JSON.parse(getUserInfo())
        this.photo = userinfo.photo
        this.obj.userId = userinfo.id

        console.log(this.obj.userId)
        console.log(userinfo)
    },
    methods: {
        handlePictureChange(file){
            console.log(file);
            const isJPG = file.raw.type === 'image/jpeg'
            const isPNG = file.raw.type === 'image/png'
            if (!isPNG && !isJPG) {
          this.$message.error('上传图片只能是 JPG/PNG 格式!')
          this.showUploadIcon = true;
          return false
        } else {
            this.imageUrl = URL.createObjectURL(file.raw);//赋值图片的url，用于图片回显功能
          this.showUploadIcon = false; 
          this.uploadImg(file)//调用上传文件api接口
        }
        },
        uploadImg(file){//文件上传操作
        let formData=new FormData()
        formData.append('file',file.raw)
        upload(formData).then(res=>{
            if (res.data.code == '200') {
                this.obj.picture = res.data.data
            	console.log(this.obj.picture)
            }
        })
      },
        getTagList() {
            getAllTagList().then(res => {
                this.tageList = res.data.data
            })
        },
        getCategory() {
            const params = {
                pageSize: 10,
                pageNum: 1,
            }
            getCategoryList(params).then(res => {
                this.categoryList = res.data.data.list
                console.log(this.categoryList)
            })
        },
        // 定义按钮点击事件的方法
        showDialog() {
            // 设置 dialogVisible 为 true，显示弹窗
            this.dialogVisible = true;
        },
        // 弹窗确认按钮点击事件的方法
        handleConfirm() {
            // 在这里编写弹窗确认按钮点击事件的逻辑
            this.obj.status = 1
            console.log(this.obj)
            save(this.obj).then(res=>{
                if(res.code==200){
                    this.$message(上传成功)
                }
            })
            // 关闭弹窗
            this.dialogVisible = false;
        },
        // 弹窗关闭前的钩子函数
        handleClose(done) {
            // 可以在这里添加弹窗关闭前的逻辑，如果需要的话
            done(); // 关闭弹窗
        },
        selectedCategory(id) {
            console.log(id)
            // this.categoryModel = this.categoryList.find(category => category.id === id);
            this.obj.categoryId = id
            // console.log(this.categoryModel)
            // return this.categoryModel.categoryName
        },
        selectRedio(value) {
            // 当用户选择选项时，此方法会被调用，并且 value 参数是选中的选项的值
            console.log('用户选择的选项值：', value);
            if (value === '1' | value == '3') {
                // 如果选中的值为 1，则显示输入框
                this.showInput = true;
            } else {
                // 否则隐藏输入框，并清空输入框中的值
                this.showInput = false;
                this.obj.sourceUrl = '';
            }
        },
        selectedTag() {
            //选择Tag时的方法
        },
    }
}
</script>

<style scoped>
.header,
.left-box {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
}

.header {
    padding: 0 27px;
    height: 5.334rem;
    background-color: #fff;
    border-bottom: 1px solid #ddd;
    z-index: 100;
}

.title-input {
    -webkit-box-flex: 1;
    -ms-flex: 1 1 auto;
    flex: 1 1 auto;
    height: 100%;
}

.title-input {
    margin: 0;
    padding: 0;
    font-size: 24px;
    font-weight: 500;
    color: #1d2129;
    border: none;
    outline: none;
}

.right-box {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    -webkit-box-pack: end;
    -ms-flex-pack: end;
    justify-content: flex-end;
}

.topic-icon {
    margin-right: 40px;
}

.with-padding {
    margin-left: 8px;
    margin-right: 8px;
}


.with-padding {
    margin-left: 8px;
    margin-right: 8px;
}

.xitu-btn {
    height: 32px;
    padding: 2px 16px;
    font-size: 14px;
    line-height: 22px;
    border: 1px solid #1d7dfa;
    border-radius: 2px;
    cursor: pointer;
    color: #fff;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background-color: #1d7dfa;
}

button,
select {
    text-transform: none;
}

button,
input {
    overflow: visible;
}

button,
input,
select,
textarea {
    font: inherit;
    margin: 0;
}

.right-box .publish-popup[data-v-7dbb341c] {
    min-width: 62px;
}

.with-padding[data-v-7dbb341c] {
    margin-left: 8px;
    margin-right: 8px;
}

.panel,
.publish-popup {
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}

.publish-popup {
    position: relative;
}

.xitu-btn {
    height: 32px;
    padding: 2px 16px;
    font-size: 14px;
    line-height: 22px;
    border: 1px solid #1d7dfa;
    border-radius: 2px;
    cursor: pointer;
    color: #fff;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    background-color: #1d7dfa;
}

.with-padding:last-child {
    padding-right: 0;
}

.with-padding {
    margin-left: 8px;
    margin-right: 8px;
}

.navigator {
    position: relative;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}







.category{
    margin-bottom: 10px;
    display: flex;
}
.tag{
    margin-bottom: 10px;
    display: flex;
}
.upload{
    margin-bottom: 10px;
    display: flex;
    position: relative;
}


.articleEdit{
    margin-bottom: 10px;
    display: flex;
}
.articleType{
    margin-bottom: 10px;
    display: flex;
}
.articleAdress{
    margin-bottom: 10px;
    display: flex;
}

.label {
    margin-right: 10px; 
    width: 100px;

}

.upload-demo {
    width: 120px; /* 设置宽度 */
    height: 80px; /* 设置高度 */
    border: 2px solid #ccc; /* 设置边框 */
    border-radius: 4px; /* 设置边框圆角 */
    box-sizing: border-box; /* 设置盒子模型为边框模型，使边框不会增加元素的实际尺寸 */
    position: relative;
}
.avatar{
    width: 120px; /* 设置宽度 */
    height: 80px; /* 设置高度 */
}

.upload-icon-up {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

.md {
    text-align: left;
}
</style>