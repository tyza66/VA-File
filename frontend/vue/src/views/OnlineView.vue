<template>
  <div>
    <div class="header">
      当前文件>{{ nowPath }}
    </div>
    <div v-show="nowPath!='/'">
      <iframe :src="srcUrl" frameborder="0" width="100%" height="100%" style="height: 80vh;"></iframe>
    </div>
    <div v-show="nowPath=='/'">
      您未选择打开的文件，或您访问的文件不存在，请返回其他界面选择文件，或通过其他用户分享的连接访问文件。
    </div>
  </div>
</template>

<style>
.header{
  height:30px;
  line-height: 30px;
}
</style>

<script>
import { request } from '@/utils/request';

export default {
  name: 'OnlineView',
  components: {
  },
  data() {
    return {
      nowPath: "/",
      srcUrl:""
    }
  },
  created() {
    this.checkLogin()
    this.checkPath()
  },
  mounted(){
  },
  methods: {
    checkLogin() {
      request.get('/user/checkLogin',{
        headers:{
          "satoken": this.getCookie("satoken")
        }
      }
      ).then(res => {
        //console.log(res);
        if(res.code != 200) {
          this.$router.push('/login')
        }
      }).catch(err => {
        console.log(err);
      })
    },getCookie(key) {
        // 将所有cookie键和值存储在一个数组中  
        var cookies = document.cookie.split(';');
        // 遍历数组，查找指定键的cookie  
        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i].trim();
            // 检查cookie是否以键值对的形式存在  
            if (cookie.indexOf('=')) {
                var cookieParts = cookie.split('=');
                // 如果cookie的第一个部分等于指定的键，则返回cookie的值  
                if (cookieParts[0] === key) {
                    return cookieParts[1];
                }
            }
        }
        // 如果没有找到指定的cookie键，则返回null或任何你指定的默认值  
        return null;
    },checkPath() {
      var path = this.getQueryVariable("path")
      console.log(path);
      if (path) {
        this.nowPath = path
        setTimeout(()=>{
          this.reflash()
        },100)
      } else {
        this.nowPath = "/"
      }
  },
  getQueryVariable(variable) {
      //获取当前URL中的查询字符串部分（即问号后面的部分）
      var query = window.location.search.substring(1);
      //把查询字符串按照&amp;符号分割成数组
      var vars = query.split("&amp;");
      //遍历其中中的每个元素
      for (var i = 0; i < vars.length; i++) {
        //把当前元素按照=符号分割成键和值
        var pair = vars[i].split("=");
        //如果当前元素的键等于参数名，则返回对应的值
        if (pair[0] == variable) {
          return decodeURI(pair[1]);
        }
      }
      //如果没有找到匹配的参数名，则返回false
      return false;
    },reflash(){
      this.srcUrl = "http://localhost:9090/file/filesOnline?partPath=" + this.nowPath
    }
}
}
</script>
