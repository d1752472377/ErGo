<template>
    <div class="comment">
        <div class="wenzi">
            <h2>共 {{ allComment }} 条评论</h2>
        </div>
        <div class="comment-header">
            <el-tooltip class="item" effect="dark" content="点我更换头像" placement="top-start">
                <div @click="handleClick">
                    <input type="file" style="display: none" @change="dealWithdAvatar" ref="avatar" />
                    <el-avatar :src="userPhoto
                        ? userPhoto
                        : 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
                        " :size="40"></el-avatar>
                </div>
            </el-tooltip>
            <el-input :placeholder="placeholderText" v-model="context" class="input" type="textarea" resize="none"
                size="mini" :maxlength="contentLength" @focus="isShowSecReply(undefined)"></el-input>
            <el-button type="info" style="height: 40px" @click="addComment(articleId, undefined)">{{ buttonText
            }}</el-button>
        </div>
        <div class="comment-body" v-for="(item, index) in comments" :key="item.id + '' + index">
            <!-- 一级评论 -->
            <div class="first-comment">
                <el-avatar :size="40" :src="item.photo"></el-avatar>
                <div class="content">
                    <!-- 一级评论用户昵称 -->
                    <h3>{{ item.userName }}</h3>
                    <!-- 一级评论发布时间 -->
                    <span>{{ item.createTime }}</span>
                    <!-- 一级评论评论内容 -->
                    <p>{{ item.content }}</p>
                    <!-- 一级评论评论点赞 -->
                    <div class="comment-right">
                        <!-- <i class="el-icon-trophy" @click="giveALike(item, item.id)"
                            :class="item.favour.includes(userId) ? 'active' : ''"></i>
                        {{ item.favour.length || 0 }} -->
                        <i class="el-icon-chat-dot-round" @click="isShowSecReply(item.id)">回复</i>
                        <i class="el-icon-delete" @click="deleteComment(item.id, undefined)"
                            v-if="userId === item.userId">删除</i>
                    </div>
                    <!-- 回复一级评论 -->
                    <div class="reply-comment" v-show="isShowSec === item.id">
                        <el-input :placeholder="placeholderText" class="input" v-model.trim="replyContext"
                            :maxlength="contentLength"></el-input>
                        <el-button type="info" size="mini" class="reply-button"
                            @click="addComment(item.id, item.userName)">回复</el-button>
                    </div>
                    <!-- 次级评论 -->
                    <div class="second-comment" v-for="(reply, index) in item.replyComments" :key="reply.id + '' + index">
                        <!-- 次级评论头像,该用户没有头像则显示默认头像 -->
                        <el-avatar :size="40" :src="reply.photo"></el-avatar>
                        <div class="content">
                            <!-- 次级评论用户昵称 -->
                            <h3>{{ reply.userName }}</h3>
                            <!-- 次级评论评论时间 -->
                            <span>{{ reply.createTime }}</span>
                            <span class="to_reply">{{ reply.userName }}</span>
                            回复
                            <span class="to_reply">{{ reply.parentName }}</span>:
                            <p>{{ reply.content }}</p>
                            <!-- 次级评论评论点赞 -->
                            <div class="comment-right">
                                <!-- <i class="el-icon-trophy" @click="giveALike(reply, item.id)"
                                    :class="reply.favour.includes(userId) ? 'active' : ''"></i>
                                {{ reply.favour ? reply.favour.length : 0 }} -->
                                <i class="el-icon-chat-dot-round" @click="isShowSecReply(reply.id)">回复</i>
                                <i class="el-icon-delete" @click="deleteComment(item.id, reply.id)"
                                    v-if="userId === reply.userId">删除</i>
                            </div>
                            <div class="reply-comment" v-show="isShowSec === reply.id">
                                <el-input :placeholder="placeholderText" class="input" v-model.trim="replyContext"
                                    :maxlength="contentLength"></el-input>
                                <el-button type="info" size="mini" class="reply-button"
                                    @click="addComment(reply.id, reply.userName)">回复</el-button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 暂无评论的空状态 -->
        <el-empty :description="emptyText" v-show="comments.length === 0"></el-empty>
        <div class="block">

            <el-pagination @current-change="handleCurrentChange" small :current-page.sync="pageNum" :page-size="pageSize"
                layout="total, prev, pager, next" :page-count="totalPage">
            </el-pagination>
        </div>
    </div>
</template>
<script>
import { list, addComment } from '@/api/comment';
import { getUserInfo } from '@/utils/auth';

export default {
    props: {
        articleId: {
            //评论所属文章 id
            type: String
        },
        emptyText: {
            // 评论为空的时候显示的文字
            type: String,
            default: "期待你的评论！"
        },
        buttonText: {
            // 按钮文字
            type: String,
            default: "评论"
        },
        contentLength: {
            // 评论长度
            type: Number,
            default: 150
        },
        placeholderText: {
            // 默认显示文字
            type: String,
            default: "请输入最多150字的评论..."
        }
    },
    data() {
        return {
            comments: {}, // 获取得到的评论
            context: "", // 评论内容
            replyContext: "", //一级评论回复
            isShowSec: "", //是否显示次级回复框
            isClickId: "", //记录点击回复的评论id
            userId: "1", // 浏览器指纹
            userName: "孤城浪人", //你的用户名
            firstIdx: 1,
            secIdx: 1,
            pageNum: 1,
            pageSize: 10,
            totalPage: 0,
            allComment: '',
            userPhoto:
                "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
        };
    },
    created() {
        // 获取评论数据
        this.getCommentList(this.$route.params.articleId);
        this.getUser()
    },
    methods: {
        getUser() {

            const a = {
                "id": 8, "userName": "爆裂的橘子",
                "email": null, "phone": "13154101341", "status": true,
                "photo": "https://cdn.tobebetterjavaer.com/paicoding/avatar/0042.png",
                "userRole": 1
            }
            const info = JSON.parse(getUserInfo())
            if (info != null) {
                this.userPhoto = info.photo
                this.userId = info.id
            } else {
                this.userPhoto = null
            }
        },
        // 唤起文件选择
        handleClick() {
            this.$refs.avatar.click();
        },
        dealWithdAvatar(e) {
            const maxSize = 2 * 1024 * 1024;
            const file = Array.prototype.slice.call(e.target.files)[0];
            console.log(file);
        },
        // async handleSizeChange(newSize) {
        //     // 用户改变每页显示条目数量时触发的方法
        //     // 更新每页显示条目数量并重新请求数据
        //     this.pageSize = newSize;
        //     await this.getCommentList(this.articleId, this.pageSize, newPage);
        // },
        async handleCurrentChange(val) {
            // 用户点击分页器上的页码时触发的方法
            // 更新当前页码并重新请求数据
            this.pageNum = val;

            await this.getCommentList(this.$route.params.articleId, val, this.pageSize);
        },
        loadData() {
            // 模拟请求数据的方法
            console.log(`加载第 ${this.pageNum} 页，每页 ${this.pageSize} 条数据`);
            // 在这里进行实际的数据请求，根据 pageNum 和 pageSize 来获取对应的数据
        },

        // 获取本篇文章所有评论
        async getCommentList(articleId) {
            const params = {
                articleId: articleId,
                pageSize: this.pageSize,
                pageNum: this.pageNum
            }
            list(params).then(res => {
                this.comments = res.data.data.commentList
                this.totalPage = res.data.data.totalPage
                this.allComment = res.data.data.allComment
                this.setTotal()
            })
        },
        setTotal:function(){
            this.$emit('transfer',this.allComment)
        },
        // 评论点赞
        giveALike(item, id) {
            try {
                // 不允许同一个人重复点赞
                if (item.favour?.includes(this.userId)) {
                    this.$message.info("您已经点过赞啦！");
                    return;
                }
                //判断是给一级评论点赞还是二级评论，只有二级评论会有parentName
                if (item.parentName) {
                    // 给二级评论点赞，向后台提交数据
                } else {
                    // 一级评论点赞，向后台提交数据
                }
                item.favour.push(this.userId);
            } catch (err) {
                this.$message.error(err);
            }
        },
        isShowSecReply(id) {
            if (id) {
                this.isShowSec = id;
                if (this.isClickId === this.isShowSec) {
                    this.isShowSec = "";
                } else {
                    this.isShowSec = id;
                }
                this.isClickId = this.isShowSec;
            } else {
                this.isShowSec = this.isClickId = "";
            }
        },
        deleteComment(id, replyId) {
            if (replyId) {
                // 删除二级评论，提交请求到后端

                // 成功后从本地记录中删除该评论
                const temp = this.comments.find(item => item.id == id).replyComments;
                for (let i = 0; i < temp.length; i++) {
                    if (temp[i].id == replyId) {
                        temp.splice(i, 1);
                        break;
                    }
                }
            } else {
                // 删除一级评论，提交请求到后端

                // 成功后从本地记录中删除该评论
                for (let i = 0; i < this.comments.length; i++) {
                    if (this.comments[i].id == id) {
                        this.comments.splice(i, 1);
                    }
                }
            }
        },
        async addComment(id, parentName) {
            try{
                let res = {}
                if(parentName){
                    if (!this.replyContext) {
                        this.$message({message:"评论或留言不能为空哦！",type: 'warning', customClass: 'global-messageZindex'})
                        return
                    }
                    // 构建要发送的评论数据
                    const commentData = {
                        articleId: this.$route.params.articleId,
                        userId: this.userId,
                        parentCommentId: id,
                        content: this.replyContext
                    };
                    addComment(commentData).then(res=>{
                        this.getCommentList(this.$route.params.articleId);
                     })

                }else{
                    if (!this.context) {
                        this.$message({message:"评论或留言不能为空哦！",type: 'warning', customClass: 'global-messageZindex'})
                        return
                    } 
                     // 构建要发送的评论数据
                     const commentData ={
                        articleId: this.$route.params.articleId,
                        userId:this.userId,
                        content: this.context,
                        parentCommentId:0
                     }
                     // 向后端发送 POST 请求保存评论数据
                     addComment(commentData).then(res=>{
                        this.getCommentList(this.$route.params.articleId);
                     })
                }
            }catch{

            }
        }

    }
};
</script>
  
<style  scoped>
.wenzi {
    margin-bottom: 10px;
    text-align: left;
}

.wenzi h2 {
    margin-bottom: 5px;
}

.wenzi h2::after {
    content: "";
    display: block;
    width: 100%;
    height: 1px;
    background-color: #aaa;
    margin-top: 5px;
}

.comment {
    min-height: 26vh;
    border-radius: 5px;
    margin-top: 2px;
    overflow: hidden;
}

.comment.active {
    color: rgb(202, 4, 4);
}

.comment .comment-header {
    position: relative;
    height: 50px;
    padding: 10px 5px;
    display: flex;
    align-items: center;
}

.comment .comment-header .input {
    margin-left: 10px;
    margin-right: 20px;
    flex: 1;
}

.comment .comment-header .input .el-input__inner:focus {
    border-color: #dcdfe6;
}

.comment .comment-body {
    min-height: 70px;
    padding: 10px 20px;
    font-size: 14px;
}

.comment .comment-body .first-comment {
    display: flex;
}

.comment .comment-body .first-comment .input .el-input__inner:focus {
    border-color: #dcdfe6;
}

.comment .comment-body .first-comment i {
    margin-right: 5px;
    margin-left: 1vw;
    cursor: pointer;
}

.comment .comment-body .first-comment i:nth-child(3) {
    color: rgb(202, 4, 4);
}

.comment .comment-body .first-comment .content {
    margin-left: 10px;
    position: relative;
    flex: 1;
}

.comment .comment-body .first-comment .content>span {
    font-size: 12px;
    color: rgb(130, 129, 129);
}

.comment .comment-body .first-comment .content .comment-right {
    position: absolute;
    right: 0;
    top: 0;
}

.comment .comment-body .first-comment .content .reply-comment {
    height: 60px;
    display: flex;
    align-items: center;
}

.comment .comment-body .first-comment .content .reply-comment .reply-button {
    margin-left: 20px;
    height: 35px;
}

.comment .comment-body .first-comment .content .second-comment {
    display: flex;
    padding: 10px 0 10px 5px;
    border-radius: 20px;
    background: #ffffff;
}

.comment .comment-body .first-comment .content .second-comment .to_reply {
    color: rgb(126, 127, 128);
}
</style>
  