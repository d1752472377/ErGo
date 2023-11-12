<template>
    <div class="section-title">
        <div class="title">
            <span class="main-title">{{ mainTitle }}</span>
            <span class="vertical-line"></span>
            <span class="sub-title" v-if="subTitle != null"
                :style="mainTitle == '搜索结果' ? 'color:red;font-weight:bold' : ''">{{ subTitle }}</span>
            <span class="view-more" v-if="btnFlag"><Button @click="$router.push(tipHref)" type="success" ghost>发布{{ tipText
            }}</Button></span>
        </div>
        <div class="menu">
            <slot name="menu"></slot>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        mainTitle: "",
        subTitle: "",
        tipText: "",
        tipHref: "",
        btnFlag: false,
    },
};
</script>

<style></style>