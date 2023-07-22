<template>
  <el-config-provider :locale="locale">
    <div>
      <Header />
      <div style="display:flex;">
        <router-view style="flex:1;"/>
      </div>
    </div>
  </el-config-provider>
</template>

<style>
</style>

<script>
import Header from "@/components/Header"
import { ElConfigProvider } from 'element-plus'
import zhCn from 'element-plus/lib/locale/lang/zh-cn'
export default {
  name: "Layout",
  components: {
    Header,
    [ElConfigProvider.name]: ElConfigProvider
  },
  data(){
    return {
      locale: zhCn
    }
  }
}
</script>