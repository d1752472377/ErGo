<template>
    
<section class="com-2-panel side J-authorPanel internal">
    <header class="com-2-panel-hd"><h3 class="com-2-panel-title">作者介绍</h3></header>
    <div class="com-2-panel-bd">
        <div class="com-author-intro">
            <div class="com-author-intro-object">
                <img class="com-author-intro-avatar" :src="avatar">
            </div>
            <h3 class="com-author-intro-name">
                <a href="/user/2069" :title="userName">{{userName}}</a>
            </h3>
            <div class="com-author-intro-desc">
                <span>已加入{{histtoryDate}}天</span>
                <div class="com-verification">
                    <img class="verified" src="../assets/icon/wan_vip.svg">
                </div>
            </div>
            <div class="com-author-intro-btns">
                
                <button @click="followUser"  class="c-btn c-btn-hole">
                    <span class="text">{{ followStatus ? '已关注' : '关注' }}</span>
                </button>
            </div>
            <ul class="com-author-intro-infos">
                <li class="com-author-intro-info">
                    <a href="/user/2069" class="com-author-intro-info-link">
                        <div class="com-author-intro-info-title">文章数</div>
                        <div class="com-author-intro-info-num">{{ articleCount }}</div>
                    </a>
                </li>
                <li class="com-author-intro-info">
                    <a href="/user/2069" class="com-author-intro-info-link">
                        <div class="com-author-intro-info-title">点赞数</div>
                        <div class="com-author-intro-info-num">{{ likes }}</div>
                    </a>
                </li>
                <li class="com-author-intro-info">
                    <a href="/user/2069" class="com-author-intro-info-link">
                        <div class="com-author-intro-info-title">收藏数</div>
                        <div class="com-author-intro-info-num">{{collection}}</div>
                    </a>
                </li>
                <li class="com-author-intro-info">
                    <a href="/user/2069" class="com-author-intro-info-link">
                        <div class="com-author-intro-info-title">粉丝数</div>
                        <div class="com-author-intro-info-num">{{ countFans }}</div>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</section>

</template>
  
<script>
import { getUserInfo } from '@/utils/auth';
import { getUserWrite, getNumberOfLikesOfArticlesWrittenByUser, getNumberOfCollectOfArticlesWrittenByUser } from '@/api/blog';
import { queryRelation,getDateForUser,followUserOrUnfollowUser,queryFollowBetweenLoggedUserAndArticleUser } from '@/api/user';

export default {
    data() {
        return {
            articleCount: '',
            countFans: '',
            likes: '',
            collection:'',
            histtoryDate:'',
            userInfo: {},
            followStatus: false,
        }
    },
    props: {
        userName: {
            type: String,
            required: true,
        },
        avatar: {
            type: String,
            required: true,
        },
        userId: {
            type: Number,
            required: true,
        },
    },
    created() {
        this.getArticle()
        this.getCountByRelation()
        this.getLikes()
        this.getCollection()
        this.getDate()
        this.getInfo()
        this.getStatus()
    },
    methods: {
        getInfo(){
            this.userInfo = JSON.parse(getUserInfo())
        },
        getCollection(){
            const params = {
                userId: this.userId
            }
            getNumberOfCollectOfArticlesWrittenByUser(params).then(res=>{
                this.collection =res.data.data
            })
        },
        getDate(){
            const params = {
                userId: this.userId
            }
            getDateForUser(params).then(res=>{
                this.histtoryDate = res.data.data
            })
        },
        getArticle() {
            const params = {
                userId: this.userId
            }
            getUserWrite(params).then(res => {
                this.articleCount = res.data.data.count
            })
        },
        getCountByRelation() {
            const params = {
                userId: this.userId
            }
            queryRelation(params).then(res => {
                this.countFans = res.data.data.countFans
            })
        },
        getLikes() {
            const params = {
                userId: this.userId
            }
            getNumberOfLikesOfArticlesWrittenByUser(params).then(res => {
                this.likes = res.data.data
            })
        },
        followUser() {
            // console.log(this.userInfo)
            var followstate = ''
            if(this.followStatus == true ){
                followstate = 2
            }else{
                followstate = 1
            }
            const params = {
                userId:this.userId,
                followUserId: this.userInfo.id,
                followState:followstate
            }
            followUserOrUnfollowUser(params).then(res=>{
                this.followStatus = !this.followStatus;
            })

        },
        getStatus(){
            const params = {
                userId:this.userId,
                followUserId: this.userInfo.id
            }
            queryFollowBetweenLoggedUserAndArticleUser(params).then(res=>{
                if(res.data.data == '已关注'){
                    console.log(res.data.data)
                    this.followStatus = true
                }else{
                    console.log("未关注")
                    this.followStatus = false
                }
                
            })
        },

    },
};
</script>
  
<style scoped>


.avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-right: 15px;
}

.com-2-panel.internal {
    position: relative;
    overflow: hidden;
  }

  .com-2-panel.side {
    padding: 20px;
  }
  .com-2-panel {
    margin-bottom: 20px;
    box-sizing: border-box;
    background-color: #fff;
    padding: 32px;
    box-shadow: 0 2px 4px 0 rgb(3 27 78 / 6%);
  }



  .com-2-panel.internal::before {
  content: "";
  position: absolute;
  width: 111px;
  height: 138px;
  /* background: url(../../img/icon-decorate_edc.svg) 50% no-repeat; */
  top: -2px;
  right: -20px;
}
.com-2-panel.side .com-2-panel-hd {
  margin-bottom: 20px;
}
.com-2-panel.side .com-2-panel-title {
  font-size: 16px;
  line-height: 26px;
  font-weight: 500;
}
.com-author-intro {
  text-align: center;
}
.com-author-intro-object {
  margin-bottom: 20px;
}
.com-author-intro-avatar:only-child {
  margin-bottom: 0;
}

.com-author-intro-avatar {
  display: block;
  margin: 0 auto -11px;
  width: 80px;
  height: 80px;
  border-radius: 50%;
  background-position: 50%;
  background-repeat: repeat;
  background-size: cover;
  background-color: #d1d4db;
  box-sizing: border-box;
  border: 1px solid #d1d4db;
}



a {
  color: #212529;
}
a {
  text-decoration: none;
}
.com-author-intro-name {
  margin-bottom: 8px;
  font-size: 16px;
  line-height: 26px;
}
a {
  color: #212529;
}
a {
  color: #212529;
  text-decoration: none;
  background-color: transparent;
}
*, ::after, ::before {
  box-sizing: border-box;
}



.com-author-intro-desc {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  box-sizing: border-box;
  max-width: 100%;
  padding-right: 20px;
  font-size: 12px;
  line-height: 18px;
  color: #999;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.com-author-intro-desc .com-verification {
  position: absolute;
  right: 0;
  top: 3px;
}
.com-verification {
  position: relative;
  top: -1px;
  display: inline-block;
  width: 12px;
  height: 12px;
  font-size: 0;
  vertical-align: middle;
  margin: -1px 3px 0;
  line-height: 12px;
}
.com-verification .verified {
  display: inline-block;
  width: 100%;
  height: 100%;
  background-image: url(../assets/icon/wan_vip.svg);
}
.com-author-intro-btns {
  margin-top: 20px;
  font-size: 0;
}
.com-author-intro-btns .c-btn {
  min-width: 88px;
  margin: 0 5px;
}




.c-btn-hole {
  background-color: transparent;
  line-height: 30px;
}
.c-btn-hole, .c-btn-hole:hover {
  border: 1px solid #56a8ff;
  color: #56a8ff;
}
.c-btn {
  height: 32px;
  min-width: 88px;
  padding: 0 16px;
  background-color: #63bcff;
  border: 1px solid transparent;
  color: #fff;
  font-size: 14px;
  line-height: 30px;
  text-align: center;
  display: inline-block;
  cursor: pointer;
  outline: 0 none;
  box-sizing: border-box;
  border-radius: 0;
}
a {
  color: #212529;
}
a {
  color: #212529;
  text-decoration: none;
  background-color: transparent;
}
.com-author-intro-infos {
  margin-top: 28px;
  padding-top: 15px;
  border-top: 1px solid #e5e5e5;
  font-size: 0;
}
ul {
  margin: 0;
    margin-top: 0px;
  padding: 0;
    padding-top: 0px;
}
.com-author-intro-info {
  display: inline-block;
  vertical-align: top;
  width: 24%;
  text-align: center;
  font-size: 12px;
  line-height: 20px;
  color: #999;
  padding-top: 5px;
  padding-bottom: 5px;
}
li {
  list-style: none;
}

</style>
  