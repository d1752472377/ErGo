<template>
    <div class="cloud" v-if="hotTagData.length > 0">
        <h2 class="hometitle">标签云</h2>
        <ul>
            <a v-for="item in hotTagData" :key="item.uid" href="javascript:void(0);" @click="goToList(item.id)">{{item.tagName}}</a>
        </ul>
    </div>
</template>

<script>
import { getHotTag } from '@/api/tag';
export default {
    name: "TagCloud",
  data() {
    return {
      hotTagData: [] //标签列表
    };
  },
  created() {
    getHotTag().then(response => {
      
      if (response.data.code == 200) {
        this.hotTagData = response.data.data;
      }
    });
  },
  methods: {
    //跳转到搜索详情页
    goToList(uid) {
      let routeData = this.$router.resolve({ path: "/list", query: { tagUid: uid } });
      window.open(routeData.href, '_blank');
    },
  }
}
</script>

<style>
.cloud { clear: both; overflow: hidden; background: #fff; margin-bottom: 20px }
.cloud ul { overflow: hidden; font-size: 14px }
.cloud ul a { line-height: 24px;  display: block; background: #999; float: left; padding: 3px 11px; margin: 10px 10px 0 0; border-radius: 8px; -moz-transition: all .5s ease; -webkit-transition: all .5s ease; -ms-transition: all .5s ease; -o-transition: all .5s ease; transition: all .5s ease; transition: all 0.5s; color: #FFF }
.cloud ul a:nth-child(8n-7) { background: #8A9B0F }
.cloud ul a:nth-child(8n-6) { background: #EB6841 }
.cloud ul a:nth-child(8n-5) { background: #3FB8AF }
.cloud ul a:nth-child(8n-4) { background: #FE4365 }
.cloud ul a:nth-child(8n-3) { background: #FC9D9A }
.cloud ul a:nth-child(8n-2) { background: #EDC951 }
.cloud ul a:nth-child(8n-1) { background: #C8C8A9 }
.cloud ul a:nth-child(8n) { background: #83AF9B }
.cloud ul a:first-child { background: #036564 }
.cloud ul a:last-child { background: #3299BB }
.cloud ul a:hover { border-radius: 0; text-shadow: #000 1px 1px 1px }
</style>