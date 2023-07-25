<template>
  <div>
    <div class="header">giao1</div>
  </div>
</template>

<script>
import { request } from '@/utils/request';

export default {
  name: 'PDFView',
  components: {
  },
  data() {
    return {
    }
  },
  created() {
    this.checkLogin()
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
    }
  }
}
</script>
