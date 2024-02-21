<template>
    <div class="content">
        <div class="header">
            <el-card>
                <div class="flex">
                    <div class="block">
                        <el-avatar :size="100" :src="circleUrl"></el-avatar>
                        <h3>WECHAT-ss5ziu</h3>
                        <p>这家伙很懒，什么都没有留下<el-button type="text"><i class="el-icon-edit"></i></el-button></p>
                    </div>
                    <div class="count">
                        <div class="itop">
                            <div class="item">
                                <p><span class="highlighted">加入天数</span></p>
                                <p>120</p>
                            </div>
                            <div class="divider"></div> <!-- 中间的分割线 -->
                            <div class="item">
                                <p><span class="highlighted">粉丝数</span></p>
                                <p>100</p>
                            </div>
                            <div class="divider"></div> <!-- 中间的分割线 -->
                            <div class="item">
                                <p><span class="highlighted">关注数</span></p>
                                <p>105</p>
                            </div>
                        </div>
                        <div class="update">

                            <el-button type="text" size="medium" @click="dialogFormVisible = true">去编辑 <i
                                    class="el-icon-arrow-right"></i></el-button>
                            <!-- Form -->
                            <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
                                <el-form :model="form">
                                    <el-form-item label="活动名称" :label-width="formLabelWidth">
                                        <el-input v-model="form.name" autocomplete="off"></el-input>
                                    </el-form-item>
                                    <el-form-item label="活动区域" :label-width="formLabelWidth">
                                        <el-select v-model="form.region" placeholder="请选择活动区域">
                                            <el-option label="区域一" value="shanghai"></el-option>
                                            <el-option label="区域二" value="beijing"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-form>
                                <div slot="footer" class="dialog-footer">
                                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                                    <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
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
        <div v-if="1+1==2">
            <el-empty description="描述文字"></el-empty>
        </div>
    </el-tab-pane>
    <el-tab-pane label="浏览记录" name="second">
        <div v-if="1+1==2">
            <el-empty description="描述文字">

            </el-empty>
        </div></el-tab-pane>
    <el-tab-pane label="关注" name="third">
        <el-tabs v-model="activeFollow" @tab-click="handleClickByFollow">
            <el-tab-pane label="关注列表" name="first">
                <div v-if="1+1==2">
            <el-empty description="描述文字"></el-empty>
        </div>
            </el-tab-pane>
            <el-tab-pane label="粉丝列表" name="second">
                <div v-if="1+1==2">
            <el-empty description="描述文字"></el-empty>
        </div>
            </el-tab-pane>
        </el-tabs>
    </el-tab-pane>
    <el-tab-pane label="收藏" name="fourth">
        <div v-if="1+1==2">
            <el-empty description="描述文字"></el-empty>
        </div>
    </el-tab-pane>
  </el-tabs>
            </el-card>
        </div>
    </div>
</template>
  
<script>
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
                desc: ''
            },
            formLabelWidth: '120px',
            activeName: 'first',
            activeFollow:'first',
        };
    },
    methods: {
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        },
        handleClick() {
        console.log('发送请求获取内容');
      },
      handleClickByFollow(){
        console.log("发送请求获取内容");
      }
    }
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
  