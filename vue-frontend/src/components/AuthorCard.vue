<template>
    <div class="author-card">
        <img :src="avatar" alt="Author Avatar" class="avatar" />
        <div class="info">
            <h3>{{ userName }}</h3>
            <p>{{ bio }}</p>
            <div class="social-links">
                <button @click="follow">关注</button>
                <button @click="sendMessage">私信</button>
            </div>
            <div class="author-stats">
                <div class="stat">
                    <div class="label">文章</div>
                    <div class="value">{{ articles }}</div>
                </div>
                <div class="stat">
                    <div class="label">点赞</div>
                    <div class="value">{{ likes }}</div>
                </div>
                <div class="stat">
                    <div class="label">粉丝</div>
                    <div class="value">{{ followers }}</div>
                </div>
            </div>
        </div>
    </div>
</template>
  
<script>
export default {
    props: {
        userName: {
            type: String,
            required: true,
        },
        avatar: {
            type: String,
            required: true,
        },
        bio: {
            type: String,
            required: true,
        },
        articles: {
            type: Number,
            required: true,
        },
        likes: {
            type: Number,
            required: true,
        },
        followers: {
            type: Number,
            required: true,
        },
    },
    methods: {
        follow() {
            // 处理关注逻辑
            console.log("关注作者");
        },
        sendMessage() {
            // 处理发送私信逻辑
            console.log("发送私信");
        },
    },
};
</script>
  
<style scoped>
.author-card {
    display: flex;
    align-items: center;
    padding: 15px;
    /* border: 1px solid #ccc; */
    border-radius: 8px;
}

.avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
}

.info {
    flex: 1;
}

h3 {
    margin-bottom: 5px;
}

.social-links button {
    margin-right: 10px;
}

.author-stats {
    display: flex;
    margin-top: 10px;
}

.stat {
    margin-right: 20px;
}

.label {
    font-size: 12px;
    color: #777;
}

.value {
    font-size: 16px;
    font-weight: bold;
}
</style>
  