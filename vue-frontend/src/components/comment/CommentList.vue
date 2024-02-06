<template>
    <div>
        <CommentList :comments="comments" />
    </div>
</template>
  
<script>
import CommentList from './CommentList.vue';
import axios from 'axios'; // 假设你使用 axios 发起请求

export default {
    name: 'ParentComponent',
    components: { CommentList },
    data() {
        return {
            comments: [],
        };
    },
    created() {
        this.fetchComments(); // 在组件创建时获取评论数据
    },
    methods: {
        fetchComments() {
            axios.get('/api/comments') // 假设评论数据的接口地址为 '/api/comments'
                .then(response => {
                    this.comments = response.data; // 将获取到的评论数据赋值给 comments
                })
                .catch(error => {
                    console.error('Error fetching comments:', error);
                });
        },
    },
};
</script>
  