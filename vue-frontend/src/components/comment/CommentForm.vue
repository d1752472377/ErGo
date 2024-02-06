<template>
    
    <div class="comment-form">
        <h3>发表评论</h3>
        <div class="form-group">
            <textarea v-model="comment" placeholder="请输入您的评论"></textarea>
        </div>
        <div class="form-group">
            <button @click="submitComment" :disabled="isSubmitting">发表评论</button>
        </div>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            comment: '',
            isSubmitting: false
        };
    },
    methods: {
        submitComment() {
            // 模拟提交评论的异步操作
            this.isSubmitting = true;
            setTimeout(() => {
                // 提交成功后的处理
                console.log('评论提交成功:', this.comment);
                // 清空评论文本框
                this.comment = '';
                this.isSubmitting = false;
                // 提示用户评论提交成功
                alert('评论提交成功！');
            }, 1000);
        }
    }
};
</script>
  
<style scoped>
.comment-form {
    margin-top: 20px;
}

.form-group {
    margin-bottom: 15px;
}

textarea,
button {
    width: 100%;
    padding: 8px;
    font-size: 14px;
}

button {
    background-color: #007bff;
    color: #fff;
    border: none;
    cursor: pointer;
}

button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
}
</style>
  