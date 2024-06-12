<template>
    <div style="text-align: left;">
        <div style="width: 1260px; margin:auto; margin-top:20px; margin-bottom:10px; text">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <span>已读消息</span>
                </div>
                <el-card v-for="article in form" :key="article.id">
                    <div style="display: flex; justify-content: space-between;">
                        <div style="display: flex;">
                            <el-avatar :size="50" :src="article.picture"></el-avatar>
                            <div style="margin-left: 20px;">
                                {{ article.username }}
                                <div style="font-size: smaller; color: grey; margin-top:5px;">{{
                                    article.content }}</div>
                            </div>
                        </div>
                    </div>
                </el-card>
            </el-card>
        </div>
        <div style="width: 1260px; margin:auto; margin-top:20px; margin-bottom:10px; text">
            <el-card>
                <div slot="header" class="clearfix">
                    <span>已读消息</span>
                </div>
            </el-card>
        </div>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            form: [
                {
                    id: '1',
                    username: '小朋友',
                    picture: 'https://p.sda1.dev/16/a697b4f2e49bd8c8aec4ced77a31d513/00000002.jpg',
                    content: '测试一下'
                },
                {
                    id: '2',
                    username: '中朋友',
                    picture: 'https://p.sda1.dev/16/b43d1cde40d973a49fff1c036115da2d/00000006.jpg',
                    content: '测试两下'
                },
                {
                    id: '3',
                    username: '大朋友',
                    picture: 'https://p.sda1.dev/16/72ae3471c0891d58fba0e3039160be8c/00000013.jpg',
                    content: '测试三下'
                },
            ]
        }
    }
}
</script>
  
<style ></style>