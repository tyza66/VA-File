<template>
  <div>
    <div class="header chat" style="heigth:40px;">
      <h2 style="text-align:center;line-height:40px;">内部聊天&文件栈</h2>
    </div>
    <div class="left">
      <div class="title">
        <h2 style="text-align:center;font-family:'宋体';height:50px;line-height:50px;">我的信息</h2>
      </div>
      <div class="left-context">
        <p>用户名：{{ user.vfmuUsername }}</p>
      </div>
    </div>
    <div class="left2">
      <div class="title">
        <h2 style="text-align:center;font-family:'宋体';height:50px;line-height:50px;">相关操作</h2>
      </div>
      <div class="left-context">
        <el-button type="default" @click="gohome()">返回主页</el-button>
        <el-button type="default" @click="goSetting()">前往设置</el-button>
        <el-button type="default" @click="goSearch()">文件检索</el-button>
        <el-button type="default" @click="changeWay()">切换优先</el-button>
        <el-button type="default" @click="clearChat()">清除聊天</el-button>
        <el-button type="default" @click="shareFile()">发布文件</el-button>
        <el-button type="default" @click="reflushFile()">刷新文件</el-button>
      </div>
    </div>
    <div class="chat-context" :class="chatWay" ref="chat">
      <div class="title" style="text-align:center;font-family:'宋体';height:50px;line-height:50px;">
        <h2>内部聊天</h2>
      </div>
      <div class="show" ref="messages1">
        <div style="heigth:30px;line-heigth:30px;margin-left: 15px;" v-for="(one, index) in infos" :key="index">{{ one }}
        </div>
      </div>
      <div class="submit">
        <el-form-item>
          <span class="input1"><el-input v-model="message" class="input"></el-input></span><el-button type="default"
            class="send" @click="send()">发送</el-button>
        </el-form-item>
      </div>
    </div>

    <div class="right" :class="fileWay" ref="file">
      <div class="title" style="text-align:center;font-family:'宋体';height:50px;line-height:50px;">
        <h2>文件栈</h2>
      </div>
      <div class="show" ref="messages2">
        <div style="heigth:30px;line-heigth:30px;margin-left: 15px;" v-for="(one, index) in files" :key="index">
          {{ one.shareMan }}:<a href="#" @click="openURL(one.downloadUrl)">{{ one.name }}</a>
        </div>
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
  height: 90px;
  background-color: #f5f5f5;
}

.left2 {
  position: fixed;
  left: 0;
  top: 220px;
  width: 200px;
  background-color: #f5f5f5;
}

.right {
  position: fixed;
  right: 0;
  top: 120px;
  width: 600px;
  height: 500px;
  background-color: #f5f5f5;
}

.left-context {
  width: 200px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.left-context p {
  margin-left: 20px;
  height: 30px;
  line-height: 30px;
}

.left-context button {
  margin: 0 5px !important;
  margin-bottom: 5px !important;
}

.chat-context {
  position: fixed;
  left: 230px;
  top: 120px;
  width: 600px;
  height: 500px;
  background-color: #f5f5f5;
}

.chat-context .show {
  width: 600px;
  height: 400px;
  background-color: #f5f5f5;
  overflow: auto;
}

.chat-context .submit {
  width: 600px;
  height: 50px;
  background-color: #f5f5f5;
}

.input {
  display: inline-block;
  width: 300px;
}

.send {
  display: inline-block;
}

.el-input__inner {
  width: 300px;
}

.input1 {
  display: inline-block;
  width: 300px;
  margin-left: 120px;
}

.front {
  z-index: 100;
}

.back {
  z-index: 99;
}

.right .show {
  width: 600px;
  height: 450px;
  background-color: #f5f5f5;
  overflow: auto;
}


.chat{
  height: 40px !important;
}
</style>

<script>
import { request } from '@/utils/request';
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'ChatView',
  components: {
  },
  data() {
    return {
      socketUrl: 'ws://127.0.0.1:9090/ws/chatRoom/',
      user: {},
      ws: null,
      users: [],
      infos: [],
      message: '',
      chatWay: 'front',
      fileWay: 'back',
      wayMode: true,
      files: []
    }
  },
  created() {
    this.checkLogin()
    this.joinChat()
    this.reflushFile()
  }, beforeDestroy() {
    this.wx.close()
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
            that.infos.push(event.data)
            that.messageButtom1()
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
    }, send() {
      var that = this
      that.ws.send(that.message)
      that.message = ''
    }, messageButtom1() {
      this.$nextTick(() => {
        let scrollEl = this.$refs.messages1;
        scrollEl.scrollTo({ top: scrollEl.scrollHeight, behavior: 'smooth' });
      });
    },
    gohome() {
      this.$router.push('/home')
    }, goSetting() {
      this.$router.push('/setting')
    },
    goSearch() {
      this.$router.push('/search')
    },
    clearChat() {
      this.infos = []
    }, changeWay() {
      if (this.wayMode) {
        this.chatWay = 'back'
        this.fileWay = 'front'
        this.wayMode = false
      } else {
        this.chatWay = 'front'
        this.fileWay = 'back'
        this.wayMode = true
      }
    }, reflushFile() {
      request.get("/share/all", {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        if (res.code == 200) {
          this.files = res.data
          this.messageButtom2()
        }
      }).catch(err => {
        console.log(err);
      })
    },
    openURL(url) {
      window.open(url);
    }, messageButtom2() {
      this.$nextTick(() => {
        let scrollEl = this.$refs.messages2;
        scrollEl.scrollTo({ top: scrollEl.scrollHeight, behavior: 'smooth' });
      });
    }, shareFile() {
      ElMessageBox.prompt('请输入分享名称', 'Tip', {
        confirmButtonText: '下一步',
        cancelButtonText: '取消'
      })
        .then(({ value }) => {
          var value1 = value
          ElMessageBox.prompt('请输入分享地址', 'Tip', {
            confirmButtonText: '提交',
            cancelButtonText: '取消'
          })
            .then(({ value }) => {
              console.log(value1, value);
              request.post("/share/file",{
                "downloadUrl": value,
                "name": value1,
              },{
                headers: {
                  "satoken": this.getCookie("satoken")
                }
              }).then(res => {
                if (res.code == 200) {
                  ElMessage({
                    type: 'success',
                    message: '分享成功',
                  })
                  this.reflushFile()
                }
              }).catch(err => {
                console.log(err);
              })
            })
            .catch(() => {
              ElMessage({
                type: 'info',
                message: '取消分享',
              })
            })
        })
        .catch(() => {
          ElMessage({
            type: 'info',
            message: '取消分享',
          })
        })
    }
  }
}
</script>
