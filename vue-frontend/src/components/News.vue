<template>
  <div class="new">
    <p>新闻

    </p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
    created(){
        this.getWeibo()
    },
    methods:{
        getWeibo(){
            var axios = require('axios');

var config = {
   method: 'get',
   url: 'https://open.tophub.today/daily',
   headers: { 
      'User-Agent': 'Apifox/1.0.0 (https://apifox.com)'
   }
};

axios(config)
.then(function (response) {
   console.log(JSON.stringify(response.data));
})
.catch(function (error) {
   console.log(error);
});

        }
            
    }
}
</script>

<style>

</style>