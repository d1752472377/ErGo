<template>
    <div>
        <v-md-editor v-model="editorValue" :disabled-menus="[]" @upload-image="handleUploadImage"
        height="calc(100vh - 150px)"></v-md-editor>
    </div>
</template>
  
<script>
import {upload} from '../../api/blog'
export default {
    name: 'mdEditor',
    data() {
        return {
            editorValue: this.content != null ? this.content : "",
        }
    },
    props: {
        // 接收值父组件传递值
        content: String
    },
    methods: {
        // v-md-editor 文件上传
        handleUploadImage(event, insertImage, files) {
            console.log(files);
            
            // 上传
            for (let i = 0; i < files.length; i++) {
                const formData = new FormData();
            formData.append('file', files[i]);
                upload(formData).then(res => {
                    // 获取返回数据
                    let data = res.data.data
                    // 添加图片到内容
                    insertImage({
                        url: data,
                    });
                });
            }
        },
    },
    watch: {
        editorValue: function (newNum, oldNum) {
            // 修改调用者传入的值
            this.$emit('update:content', newNum)
        }
    },
}
</script>
  
<style scoped>

</style>
  