<template>
  <div class="header">
    <transition name="slide-fade">
        <div id="header" v-show="show">
            <router-link id="logo" to="/">
                <!-- <img src="../../../assets/images/xiyan-logo.png" /> -->
            </router-link>
            <ul id="nav">
                <li>
                    <router-link>
                        
                    </router-link>
                </li>
            </ul>
        </div>
    </transition>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>