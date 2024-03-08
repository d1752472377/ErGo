<template>
   <div class="new">
      <div class="ques-section-item">
         <div class="top" style="
         margin-left:20px;
         margin-bottom:20px; 
         width:250px; 
         background-color: #fc5632;
         background-image: radial-gradient(circle at 50% 3%,#f07a56,#fc5632);
         height: 108px;  
         ">
            <div class="ques-card-head ques-card-red"><svg>
                  <defs>
                     <pattern id="water" width=".25" height="1.1" patternContentUnits="objectBoundingBox">
                        <path fill="#fff"
                           d="M0.25,1H0c0,0,0-0.659,0-0.916c0.083-0.303,0.158,0.334,0.25,0C0.25,0.327,0.25,1,0.25,1z">
                        </path>
                     </pattern>
                     <g id="eff">
                        <rect fill="url(#water)" x="-119.461" y="0" width="1200" height="120" opacity="0.3">
                           <animate attributeType="xml" attributeName="x" from="-300" to="0" repeatCount="indefinite"
                              dur="10s"></animate>
                        </rect>
                        <rect fill="url(#water)" y="5" width="1600" height="125" opacity="0.3" x="-399.447">
                           <animate attributeType="xml" attributeName="x" from="-400" to="0" repeatCount="indefinite"
                              dur="13s"></animate>
                        </rect>
                     </g>
                  </defs>
                  <use xlink:href="#eff" opacity="1" style="mix-blend-mode: normal;"></use>
               </svg></div>
            <div style="font-size: 18px;
            font-weight: 500;
            line-height: 24px;
            position: relative;
            padding-top: 15px;
            color: #fff;">
               <h3>CSDN热搜</h3>
            </div>

         </div>
         <div class="ques-section-card" v-for="article in csdnData" :key="article.keyword">
            <ul class="ques-card-list">
               <el-link :underline="false" class="main-text" :href="article.url" target="_blank"> {{ article.keyword
               }}</el-link>
            </ul>
         </div>

      </div>
      <div class="ques-section-item">
         <div class="top" style="
         margin-left:20px; 
         margin-bottom:20px; 
         width:250px; 
         background-color: #4e6ef2;
         background-image: radial-gradient(circle at 50% -45%,#53a7f5,#4e6ef2);
         height: 108px;
         ">
            <div class="ques-card-head ques-card-red"><svg>
                  <defs>
                     <pattern id="water" width=".25" height="1.1" patternContentUnits="objectBoundingBox">
                        <path fill="#fff"
                           d="M0.25,1H0c0,0,0-0.659,0-0.916c0.083-0.303,0.158,0.334,0.25,0C0.25,0.327,0.25,1,0.25,1z">
                        </path>
                     </pattern>
                     <g id="eff">
                        <rect fill="url(#water)" x="-119.461" y="0" width="1200" height="120" opacity="0.3">
                           <animate attributeType="xml" attributeName="x" from="-300" to="0" repeatCount="indefinite"
                              dur="10s"></animate>
                        </rect>
                        <rect fill="url(#water)" y="5" width="1600" height="125" opacity="0.3" x="-399.447">
                           <animate attributeType="xml" attributeName="x" from="-400" to="0" repeatCount="indefinite"
                              dur="13s"></animate>
                        </rect>
                     </g>
                  </defs>
                  <use xlink:href="#eff" opacity="1" style="mix-blend-mode: normal;"></use>
               </svg></div>

            <div style="font-size: 18px;
            font-weight: 500;
            line-height: 24px;
            position: relative;
            padding-top: 15px;
            color: #fff;">
               <h3>CSDN热搜</h3>
            </div>
         </div>
         <div class="ques-section-card" v-for="article in baiduData" :key="article.keyword" target="_blank">
            <ul class="ques-card-list">
               <el-link :underline="false" class="main-text" :href="article.url" target="_blank">{{ article.keyword
               }}</el-link>
            </ul>
         </div>

      </div>
      <div class="ques-section-item">
         <div class="top" style="
         margin-left:20px;
         margin-bottom:20px;  
         width:250px; 
         background-color: #06f;
         background-image: radial-gradient(circle at 50% -45%,#53a7f5,#06f);
         height: 108px;
         ">
            <div class="ques-card-head ques-card-red"><svg>
                  <defs>
                     <pattern id="water" width=".25" height="1.1" patternContentUnits="objectBoundingBox">
                        <path fill="#fff"
                           d="M0.25,1H0c0,0,0-0.659,0-0.916c0.083-0.303,0.158,0.334,0.25,0C0.25,0.327,0.25,1,0.25,1z">
                        </path>
                     </pattern>
                     <g id="eff">
                        <rect fill="url(#water)" x="-119.461" y="0" width="1200" height="120" opacity="0.3">
                           <animate attributeType="xml" attributeName="x" from="-300" to="0" repeatCount="indefinite"
                              dur="10s"></animate>
                        </rect>
                        <rect fill="url(#water)" y="5" width="1600" height="125" opacity="0.3" x="-399.447">
                           <animate attributeType="xml" attributeName="x" from="-400" to="0" repeatCount="indefinite"
                              dur="13s"></animate>
                        </rect>
                     </g>
                  </defs>
                  <use xlink:href="#eff" opacity="1" style="mix-blend-mode: normal;"></use>
               </svg></div>

            <div style="font-size: 18px;
            font-weight: 500;
            line-height: 24px;
            position: relative;
            padding-top: 15px;
            color: #fff;">
               <h3>知乎热搜</h3>
            </div>
         </div>
         <div class="ques-section-card" v-for="article in zhihuData" :key="article.keyword">
            <ul class="ques-card-list">
               <el-link :underline="false" class="main-text" :href="article.url" target="_blank">{{ article.keyword
               }}</el-link>
            </ul>
         </div>

      </div>
      <div class="ques-section-item">
         <div class="top" style="
         margin-left:20px; 
         margin-bottom:20px; 
         width:250px; 
         background-color: #d52c2b;
         background-image: radial-gradient(circle at 50% -45%,#d63736,#d52c2b);
         height: 108px;  
         ">
            <div class="ques-card-head ques-card-red"><svg>
                  <defs>
                     <pattern id="water" width=".25" height="1.1" patternContentUnits="objectBoundingBox">
                        <path fill="#fff"
                           d="M0.25,1H0c0,0,0-0.659,0-0.916c0.083-0.303,0.158,0.334,0.25,0C0.25,0.327,0.25,1,0.25,1z">
                        </path>
                     </pattern>
                     <g id="eff">
                        <rect fill="url(#water)" x="-119.461" y="0" width="1200" height="120" opacity="0.3">
                           <animate attributeType="xml" attributeName="x" from="-300" to="0" repeatCount="indefinite"
                              dur="10s"></animate>
                        </rect>
                        <rect fill="url(#water)" y="5" width="1600" height="125" opacity="0.3" x="-399.447">
                           <animate attributeType="xml" attributeName="x" from="-400" to="0" repeatCount="indefinite"
                              dur="13s"></animate>
                        </rect>
                     </g>
                  </defs>
                  <use xlink:href="#eff" opacity="1" style="mix-blend-mode: normal;"></use>
               </svg></div>

            <div style="font-size: 18px;
            font-weight: 500;
            line-height: 24px;
            position: relative;
            padding-top: 15px;
            color: #fff;">
               <h3>微博热搜</h3>
            </div>
         </div>
         <div class="ques-section-card" v-for="article in weiboData" :key="article.keyword">
            <ul class="ques-card-list">
               <el-link :underline="false" class="main-text" :href="article.url" target="_blank">{{ article.keyword
               }}</el-link>
            </ul>
         </div>

      </div>
      <div class="ques-section-item">
         <div class="top" style="
         margin-left:20px; 
         margin-bottom:20px; 
         width:250px; 
         background-color: #f04142;
         background-image: radial-gradient(circle at 50% -6%,#f24e4f 2%,#f04142);
         height: 108px; 
         ">
            <div class="ques-card-head ques-card-red"><svg>
                  <defs>
                     <pattern id="water" width=".25" height="1.1" patternContentUnits="objectBoundingBox">
                        <path fill="#fff"
                           d="M0.25,1H0c0,0,0-0.659,0-0.916c0.083-0.303,0.158,0.334,0.25,0C0.25,0.327,0.25,1,0.25,1z">
                        </path>
                     </pattern>
                     <g id="eff">
                        <rect fill="url(#water)" x="-119.461" y="0" width="1200" height="120" opacity="0.3">
                           <animate attributeType="xml" attributeName="x" from="-300" to="0" repeatCount="indefinite"
                              dur="10s"></animate>
                        </rect>
                        <rect fill="url(#water)" y="5" width="1600" height="125" opacity="0.3" x="-399.447">
                           <animate attributeType="xml" attributeName="x" from="-400" to="0" repeatCount="indefinite"
                              dur="13s"></animate>
                        </rect>
                     </g>
                  </defs>
                  <use xlink:href="#eff" opacity="1" style="mix-blend-mode: normal;"></use>
               </svg></div>

            <div style="font-size: 18px;
            font-weight: 500;
            line-height: 24px;
            position: relative;
            padding-top: 15px;
            color: #fff;">
               <h3>头条热搜</h3>
            </div>
         </div>
         <div class="ques-section-card" v-for="article in toutiaoData" :key="article.keyword">
            <ul class="ques-card-list">
               <el-link :underline="false" class="main-text" :href="article.url" target="_blank"> {{ article.keyword
               }}</el-link>
            </ul>
         </div>

      </div>
   </div>
</template>

<script>
import { getBaidu, getBili, getcsdn, getWeibo, getZhihu, getToutiao } from '@/api/api'
export default {
   data() {
      return {
         csdnData: [],
         toutiaoData: [],
         biliData: [],
         baiduData: [],
         zhihuData: [],
         weiboData: [],
      }
   },
   created() {
      this.getBaiduList()
      this.getBiliList()
      this.getCSDNList()
      this.getWeiboList()
      this.getZhihuList()
      this.getToutiaoList()
   },
   methods: {
      getCSDNList() {
         getcsdn().then(res => {

            this.csdnData = res.data.data

         })
      },
      getZhihuList() {
         getZhihu().then(res => {
            console.log("知乎", res.data)
            this.zhihuData = res.data.data

         })
      },
      getBaiduList() {
         getBaidu().then(res => {
            console.log("百度", res.data)
            this.baiduData = res.data.data
         })
      },
      getWeiboList() {
         getWeibo().then(res => {
            console.log("微博", res.data)
            this.weiboData = res.data.data
         })
      },
      getBiliList() {
         getBili().then(res => {
            console.log("哔哩", res.data)
            this.biliData = res.data
         })
      },
      getToutiaoList() {
         getToutiao().then(res => {
            console.log("头条", res.data)
            this.toutiaoData = res.data.data
         })
      },
   }
}
</script>

<style scoped>* {
   margin: 0;
   padding: 0;
}

.new {
   width: 100%;
   display: flex;
   -ms-flex-pack: center;
   justify-content: center;

}

.div {
   display: block;
}

.ques-section-item {
   margin-top: 30px;
   width: 260px;
   position: relative;
   float: left;
}

.ques-section-card {
   position: relative;
   height: 60px;
   text-align: center;
   border: 1px solid #fafafa;
   border-radius: 2px;
   margin-left: 20px;
   margin-top: 10px;

}

.ques-card-list {
   text-align: left;
   /* padding-top: 85px; */
}

li,
ul {
   list-style: none;
}


svg:not(:root) {
   overflow-clip-margin: content-box;
   overflow: hidden;
}

.ques-card-head {
   position: absolute;
   top: 0;
   left: 0;
   margin-left: 20px;
   width: 100%;
   height: 30px;
   padding-top: 48px;
   border-radius: 2px 2px 0 0;
}</style>