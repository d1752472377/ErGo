<template>
  <div class="home-content">
    <Row>
        <Col :xs="24" :sm="24" :md="24" :lg="17">
        <div class="layout-left"
        style="background-color: #ffffff; padding: 30px 20px 10px 20px">


        </div>
        </Col>
        <Col :xs="0" :sm="0" :md="0" :lg="7">
        <div class="layout-right">
            <recommend></recommend>
        </div>
        </Col>
    </Row>
  </div>
</template>

<script>
import { Col, Row } from 'view-design';
import Recommend from '../Recommend.vue';

export default {
    components: { 
        Row, 
        Col,
        recommend: Recommend,
    }
}
</script>

<style>

</style>