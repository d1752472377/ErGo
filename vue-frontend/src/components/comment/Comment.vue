<template>
    <div>
        <h3 class="ui dividing header">Comments | 共 {{ $store.state.allComment }} 条评论<span
                v-if="$store.state.closeComment !== 0">（{{ $store.state.closeComment }} 条评论被隐藏）</span></h3>
        <h3 class="ui header" v-if="$store.state.allComment === 0">快来抢沙发！</h3>
        <div class="comment" v-for="comment in $store.state.comments" :key="comment.id">
            <span class="anchor" :id="`comment-${comment.id}`"></span>
            <a class="ui circular image avatar">
                <img :src="comment.avatar">
            </a>
            <div class="content">
                <a class="nickname" :href="comment.website !== '' && comment.website !== null ? comment.website : null"
                    target="_blank" rel="external nofollow noopener">{{ comment.nickname }}</a>
                <div class="ui black left pointing label" v-if="comment.adminComment">{{
                    $store.state.siteInfo.commentAdminFlag }}</div>
                <div class="metadata">
                    <strong class="date">{{ comment.createTime | dateFormat('YYYY-MM-DD HH:mm') }}</strong>
                </div>
                <div class="text" v-html="comment.content"></div>
                <el-button size="mini" type="primary" @click="replyToComment(comment.id)">回复</el-button>
                <CommentForm :parentId="comment.id" v-if="parentCommentId === comment.id" />
            </div>
        </div>
    </div>
</template>
  
<script>
import CommentForm from './CommentForm.vue';

export default {
    name: 'CommentList',
    components: { CommentForm },
    computed: {
        parentCommentId() {
            return this.$store.state.parentCommentId;
        }
    },
    methods: {
        replyToComment(id) {
            this.$store.state.parentCommentId = id;
        },
    },
};
</script>
  
<style scoped>
.comment {
    padding-right: 1em !important;
    padding-left: 1em !important;
}

.nickname {
    font-weight: bolder;
    color: #000;
}

.ui.comments .comment .avatar {
    width: 40px !important;
    margin: 0;
}

.ui.comments .comment .text {
    white-space: pre-wrap !important;
    line-height: 1.5;
}

.label {
    cursor: default;
    padding: 4px 6px !important;
    font-weight: 500 !important;
}
</style>
  