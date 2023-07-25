<template>
  <div>
    <div class="header">
      <h2 style="text-align:center;line-height:40px;">内部聊天&文件栈</h2>
    </div>
    <div class="left">
      <div class="title">
        <h2 style="text-align:center;font-family:'宋体';height:50px;line-height:50px;">在线用户</h2>
      </div>
    </div>

    <div class="right">
      <div class="title" style="text-align:center;font-family:'宋体';height:50px;line-height:50px;">
        <h2>文件栈</h2>
      </div>
    </div>
  </div>
</template>

<style>
.left {
  position: fixed;
  left: 0;
  top: 120px;
  width: 200px;
  height: 500px;
  background-color: #f5f5f5;
}

.right {
  position: fixed;
  right: 0;
  top: 120px;
  width: 200px;
  height: 500px;
  background-color: #f5f5f5;
}
</style>

<script>
import { request } from '@/utils/request';

export default {
  name: 'ChatView',
  components: {
  },
  data() {
    return {
      socketUrl: 'ws://127.0.0.1:9090/ws/chatRoom/',
      user: {},
      ws: null
    }
  },
  created() {
    this.checkLogin()
    this.joinChat()
  },
  methods: {
    checkLogin() {
      var that = this;
      request.get('/user/checkLogin', {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }
      ).then(res => {
        //console.log(res);
        if (res.code != 200) {
          this.$router.push('/login')
        }
        if (res.code == 200) {
          that.user = res.user
        }
      }).catch(err => {
        console.log(err);
      })
    }, getCookie(key) {
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
    },
    joinChat() {
      var that = this
      request.get('/user/checkLogin', {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }
      ).then(res => {
        //console.log(res);
        if (res.code != 200) {
          this.$router.push('/login')
        }
        if (res.code == 200) {
          that.user = res.user
          that.ws = new WebSocket(that.socketUrl + that.user.vfmuUsername);
          //连接成功建立的回调方法
          that.ws.onopen = function (event) {
            console.log('建立连接')
          }

          //接收到消息的回调方法
          that.ws.onmessage = function (event) {
            console.log('接收到内容：' + event.data)
            
          }

          //连接发生错误的回调方法
          that.ws.onerror = function (event) {
            console.log('发生错误')
          }

          //连接关闭的回调方法
          that.ws.onclose = function (event) {
            console.log('关闭连接')
          }
        }
      }).catch(err => {
        console.log(err);
      })
    }
  }
}
</script>
