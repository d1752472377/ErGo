<template>
    <div class="content">
        <div class="header">
            <el-card>
                <div class="flex">
                    <div class="block">
                        <el-avatar :size="100" :src="userInfo.photo"></el-avatar>
                        <h3>{{ userInfo.userName }}</h3>
                        <p>{{ profile }}<el-button type="text"><i class="el-icon-edit"
                                    @click="dialogFormVisible = true"></i></el-button></p>
                    </div>
                    <div class="count">
                        <div class="itop">
                            <div class="item">
                                <p><span class="highlighted">加入天数</span></p>
                                <p>{{ difftime }}</p>
                            </div>
                            <div class="divider"></div> <!-- 中间的分割线 -->
                            <div class="item">
                                <p><span class="highlighted">粉丝数</span></p>
                                <p>{{ countFans }}</p>
                            </div>
                            <div class="divider"></div> <!-- 中间的分割线 -->
                            <div class="item">
                                <p><span class="highlighted">关注数</span></p>
                                <p>{{ countFollow }}</p>
                            </div>
                        </div>
                        <div class="update">

                            <el-button type="text" size="medium" @click="dialogFormVisible = true">去编辑 <i
                                    class="el-icon-arrow-right"></i></el-button>
                            <!-- Form -->
                            <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
                                <el-form>
                                    <el-form-item label="用户名" :label-width="formLabelWidth">
                                        <el-input v-model="userInfo.userName" autocomplete="off"></el-input>
                                    </el-form-item>
                                    <el-form-item label="头像" :label-width="formLabelWidth">
                                        <el-upload class="upload-demo" :show-file-list="false" :auto-upload="false"
                                            :action="''" :on-change="handlePictureChange" style="width:60px;height:60px;">


                                            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                                        </el-upload>
                                    </el-form-item>
                                    <el-form-item label="简介" :label-width="formLabelWidth">
                                        <el-input v-model="profile" autocomplete="off"></el-input>
                                    </el-form-item>
                                </el-form>
                                <div slot="footer" class="dialog-footer">
                                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                                    <el-button type="primary" @click="editUserInfo">确 定</el-button>
                                </div>
                            </el-dialog>

                        </div>
                    </div>

                </div>
            </el-card>
        </div>
        <div class="dibu">
            <el-card>
                <el-tabs v-model="activeName" @tab-click="handleClick">
                    <el-tab-pane label="文章" name="first">
                        <div v-if="follow.length == 0">
                            <el-empty description="暂无内容"></el-empty>
                        </div>
                        <div>
                            <el-row :gutter="20">
                                <el-card v-for="article in blog" :key="article.id">
                                    <div>
                                        <router-link class="main-text" :to="'/article/' + article.id"
                                            v-html="article.title"></router-link>
                                        <div class="timie" style="margin-top: 10px;">
                                            <span class="publish-time">
                                                <Icon type="ios-timer-outline" class="icon" />{{
                                                    article.createTime | formatDate
                                                }}
                                            </span>
                                        </div>
                                    </div>
                                </el-card>
                            </el-row>

                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="浏览记录" name="second">
                        <div v-if="readHistory.length == 0">
                            <el-empty description="暂无内容">
                            </el-empty>
                        </div>
                        <div>
                            <el-card v-for="article in readHistory" :key="article.id">
                                <div>
                                    <router-link class="main-text" :to="'/article/' + article.id"
                                        v-html="article.title"></router-link>
                                    <div class="timie" style="margin-top: 10px;">
                                        <span class="publish-time">
                                            <Icon type="ios-timer-outline" class="icon" />{{
                                                article.createTime | formatDate
                                            }}
                                        </span>
                                    </div>
                                </div>
                            </el-card>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="关注" name="third">
                        <el-tabs v-model="activeFollow" @tab-click="handleClickByFollow">
                            <el-tab-pane label="关注列表" name="first">
                                <div v-if="follow.length == 0">
                                    <el-empty description="暂无内容"></el-empty>
                                </div>
                                <div v-else>

                                    <el-card v-for="article in follow" :key="article.id">
                                        <div style="display: flex; justify-content: space-between;">
                                            <div style="display: flex;">
                                                <el-avatar :size="50" :src="article.photo"></el-avatar>
                                                <div style="margin-left: 20px;">
                                                    {{ article.userName }}
                                                    <div style="font-size: smaller; color: grey; margin-top:5px;">{{
                                                        article.profile }}</div>
                                                </div>
                                            </div>
                                            <el-button :type="followed ? 'danger' : 'success'"
                                                @click="toggleFollow(article)">
                                                {{ followed ? '取消关注' : '关注' }}
                                            </el-button>
                                        </div>
                                    </el-card>

                                </div>
                            </el-tab-pane>
                            <el-tab-pane label="粉丝列表" name="second">
                                <div v-if="fans.length == 0">
                                    <el-empty description="暂无内容"></el-empty>
                                </div>
                                <div v-else>
                                    <el-card v-for="article in fans" :key="article.id">
                                        <div style="display: flex; justify-content: space-between;">
                                            <div style="display: flex;">
                                                <el-avatar :size="50" :src="article.photo"></el-avatar>
                                                <div style="margin-left: 20px;">
                                                    {{ article.userName }}
                                                    <div style="font-size: smaller; color: grey; margin-top:5px;">{{
                                                        article.profile }}</div>
                                                </div>
                                            </div>
                                        </div>
                                    </el-card>

                                </div>
                            </el-tab-pane>
                        </el-tabs>
                    </el-tab-pane>
                    <el-tab-pane label="收藏" name="fourth">
                        <div v-if="collect.length === 0">
                            <el-empty description="暂无内容"></el-empty>
                        </div>
                        <div v-else>
                            <el-card v-for="article in collect" :key="article.id">
                                <div>
                                    <router-link class="main-text" :to="'/article/' + article.id"
                                        v-html="article.title"></router-link>
                                    <div class="timie" style="margin-top: 10px;">
                                        <span class="publish-time">
                                            <Icon type="ios-timer-outline" class="icon" />{{
                                                article.createTime | formatDate
                                            }}
                                        </span>
                                    </div>
                                </div>
                            </el-card>
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </el-card>
        </div>
    </div>
</template>
  
<script>
import { getUserInfo, setUserInfo } from '@/utils/auth';
import { queryRelationCount, queryRelation, updateRelation,updateUserXinxi } from '@/api/user'
import { getUserWrite, getUserFootForRead, getUserFootForCollection, upload } from '@/api/blog'


export default {
    data() {
        return {
            circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
            dialogFormVisible: false,
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: '',
                picture: ''
            },
            formLabelWidth: '120px',
            activeName: 'first',
            activeFollow: 'first',
            userInfo: {},
            profile: "这个家伙很懒，什么也没有留下",
            difftime: '',
            countFans: '',
            countFollow: '',
            fans: [],
            follow: [],
            blog: [],
            readHistory: [],
            collect: [],
            followed: true,
            imageUrl: ''
        };
    },
    created() {
        this.getUser()
        this.getCountByRelation()
        this.getBlogById()
        this.getReadById()
        this.getCollectionById()
    },
    methods: {
        getUser() {
            const userinfo1 = getUserInfo()
            this.userInfo = JSON.parse(userinfo1);
            if (this.userInfo.profile != '') {
                this.profile = this.userInfo.profile
            }
            this.imageUrl = this.userInfo.photo
            var new_date = new Date();
            var old_date = new Date(this.userInfo.createTime);
            // console.log(new_date)
            // console.log(old_date)
            this.difftime = Math.round((new_date - old_date) / 1000 / 86400)

        },
        getBlogById() {
            const params = {
                userId: this.$route.params.id
            }
            getUserWrite(params).then(res => {
                this.blog = res.data.data.list
                // console.log(this.blog)
            })
        },
        getCountByRelation() {
            const params = {
                userId: this.$route.params.id
            }
            queryRelation(params).then(res => {
                this.countFans = res.data.data.countFans
                this.countFollow = res.data.data.countFollow
                this.fans = res.data.data.fans
                this.follow = res.data.data.follow
                console.log(this.follow)

            })
        },
        getReadById() {
            const params = {
                userId: this.$route.params.id
            }
            getUserFootForRead(params).then(res => {
                this.readHistory = res.data.data
            })
        },
        getCollectionById() {
            const params = {
                userId: this.$route.params.id
            }
            getUserFootForCollection(params).then(res => {
                this.collect = res.data.data.list
                // console.log(this.collect.length)
            })
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        },
        handleClick() {
            // console.log('发送请求获取内容');
        },
        handleClickByFollow() {
            // console.log("发送请求获取内容");
        },
        toggleFollow(article) {
            const followState = this.followed ? 1 : 2;
            // console.log(article)
            const params = {
                userId: article.id,
                followUserId: this.userInfo.id,
                followState: followState
            }
            updateRelation(params).then(res => {
                this.followed = !this.followed;
            }).catch(error => {
                console.error('关注状态切换失败', error);
            });
        },
        editUserInfo() {
            const userInfo = {
                userId:this.userInfo.id,
                userName: this.userInfo.userName,
                photo: this.imageUrl,
                profile: this.profile,
            }
            updateUserXinxi(userInfo).then(res=>{
                setUserInfo(JSON.stringify(res.data.data.userInfo))
               
            })
            this.dialogFormVisible = false
        },
        handlePictureChange(file) {
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
        async uploadImg(file) {//文件上传操作
            let formData = new FormData()
            formData.append('file', file.raw)
            upload(formData).then(res => {
                console.log(res)
                if (res.data.code == '200') {
                    this.imageUrl= res.data.data.url
                    console.log(this.obj.picture)
                }
            })
        },

    },
    filters: {
        formatDate: function (value) {
            if (value != null) {
                let date = new Date(value);
                let y = date.getFullYear();
                let MM = date.getMonth() + 1;
                MM = MM < 10 ? "0" + MM : MM;
                let d = date.getDate();
                d = d < 10 ? "0" + d : d;
                let h = date.getHours();
                h = h < 10 ? "0" + h : h;
                let m = date.getMinutes();
                m = m < 10 ? "0" + m : m;
                let s = date.getSeconds();
                s = s < 10 ? "0" + s : s;
                return y + "-" + MM + "-" + d + " " + h + ":" + m + ":" + s;
            }
        },
    },
};
</script>
  
<style scoped>
.content {
    /* background-color: #868686; */
    width: 1260px;
}

.header {
    width: 1150px;
    margin: auto;
}

.flex {
    display: flex;
    justify-content: space-between;
    /* background-color: #868686; */
}

.block {
    flex: 1;

    margin-left: 10px;
}

.block p {
    overflow: hidden;
    /* 隐藏超出容器范围的内容 */
    white-space: nowrap;
    /* 防止文字换行 */
    text-overflow: ellipsis;
    /* 显示省略号以表示被省略的文本 */
    max-width: 500px;
    /* 设定 p 标签的最大宽度 */
}

.count {
    /* background-color: #868686; */
    width: 220px;

}

.itop {
    display: flex;
    margin-top: 10px;
}

.item {
    text-align: center;
    margin-right: 18px;
}

.item p {
    margin-bottom: 15px;
}

.highlighted {
    color: rgb(121, 127, 132);
    /* 设置特定字体的颜色 */
}

.update {
    margin-top: 75px;
    margin-left: 150px;
}

.dibu {
    width: 1150px;
    margin: auto;
}
</style>
  