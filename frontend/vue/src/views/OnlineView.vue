<template>
  <div>
    <div class="header">
      当前文件>{{ '/' + nowPath }}
      <el-button class="rb" type="default" size="small" @click="goSetting()">设置</el-button>
      <el-button class="rb" type="default" size="small" @click="goHome()">返回主页</el-button>
      <el-button class="rb" size="small" type="link" @click="chat()">文件分享</el-button>
    </div>
    <div class="main-all"
      v-show="(nowPath != '/' && (type == 'pdf' || type == 'txt' || type == 'png' || type == 'jepg'))">
      <iframe :src="srcUrl" frameborder="0" width="100%" height="100%" style="height: 88.2vh;"></iframe>
    </div>
    <div class="main-all" v-show="nowPath != '/' && (type == 'docx')">
      开发周期原因，暂不支持此格式，但是你可以对此文件进行操作。
    </div>
    <div v-show="nowPath == '/'">
      您未选择打开的文件，或您访问的文件不存在，请返回其他界面选择文件，或通过其他用户分享的连接访问文件。
    </div>
    <div class="left3">
      <div class="title3">
        <p>可执行操作</p>
      </div>
      <div class="control">
        <el-button type="primary" @click="reflash()">刷新文件</el-button>
        <el-button type="primary" @click="download()">下载文件</el-button>
        <el-button type="primary" @click="delete1('', 'file')">删除文件</el-button>
        <el-button type="primary" @click="goParent()">打开所在目录</el-button>
        <el-button type="primary" @click="reflash()">创建搜索索引</el-button>
        <el-button type="primary" @click="share()">快速分享文件</el-button>
        <el-button type="primary" @click="pdf2word()" v-show="type == 'pdf'">转PDF为Word</el-button>
        <el-button type="primary" @click="ocr1()" v-show="type == 'png'">OCR识别图片</el-button>
        <el-button type="primary" @click="word2pdf()" v-show="type == 'docx'">转Word为PDF</el-button>
        <el-button type="primary" @click="reflash()">激活问答系统</el-button>
      </div>
    </div>
    <div class="right3" v-show="type!='png'">
      <div class="title3">
        <p>文档内容问答</p>
      </div>
      <div class="context">

      </div>
    </div>
    <div class="right3" v-show="type=='png'">
      <div class="title3">
        <p>OCR识别结果</p>
      </div>
      <div class="context">
        <el-input
        v-model="ocr"
        placeholder="点击OCR识别按钮获得识别结果"
        show-word-limit
        type="textarea"
      />
      </div>
    </div>
  </div>
</template>

<style>
.header {
  height: 30px;
  line-height: 30px;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, .1);
  right: 0;
  z-index: 100;
}

.main-all {
  width: 60%;
  margin: 0 auto;
}

.rb {
  float: right;
  margin-left: 0px !important;
  margin-top: 3px;
  margin-right: 10px;
}

.left3 {
  width: 200px;
  position: fixed;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, .1);
  top: 150px;
  left: 0;
  z-index: 100;
}

.title3 {
  height: 30px;
  line-height: 30px;
  text-align: center;
  font-family: '宋体';
}

.control {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.control button {
  width: 80%;
  display: block;
  margin: 0 !important;
  margin-top: 5px !important;
  margin-bottom: 5px !important;
}

.right3 {
  width: 250px;
  position: fixed;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, .1);
  top: 150px;
  right: 0;
  z-index: 100;
}

.context textarea{
  height: 300px;
}
</style>

<script>
import { request } from '@/utils/request';
import { ElMessage, ElMessageBox } from 'element-plus'


export default {
  name: 'OnlineView',
  components: {
  },
  data() {
    return {
      nowPath: "/",
      srcUrl: "",
      type: "",
      ocr:""
    }
  },
  created() {
    this.checkLogin()
    this.checkPath()
  },
  mounted() {

  },
  methods: {
    checkLogin() {
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
    }, checkPath() {
      var path = this.getQueryVariable("path")
      //console.log(path);
      if (path) {
        this.nowPath = path
        setTimeout(() => {
          this.reflash()
        }, 100)
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
    }, reflash() {
      var that = this;
      request.get("/file/checkFile?partPath=" + this.nowPath, {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        if (res.code == 200) {
          var end = that.nowPath.substring(that.nowPath.lastIndexOf(".") + 1, that.nowPath.length)
          that.type = end
          if (end == "docx") {

          } else {
            that.srcUrl = "http://localhost:9090/file/filesOnline?partPath=" + that.nowPath + "&a=" + Math.random()
          }
        } else if (res.code == 201) {
          that.$message({
            message: '您未登录,一秒后自动跳转到登录界面',
            type: 'error'
          });
          setTimeout(() => {
            that.$router.push('/login')
          }, 1000)
        } else if (res.code == 199) {
          that.$message({
            message: '您访问的文件不存在，一秒后自动跳转到主页',
            type: 'error'
          });
          setTimeout(() => {
            that.$router.push('/home')
          }, 1000)
        }
      })
    }, goSetting() {
      this.$router.push('/setting')
    },
    goHome() {
      this.$router.push('/home')
    }, download() {
      var path = "http://localhost:9090/file/download.action?partPath=" + this.nowPath
      //console.log(path);
      window.open(path)
    }, delete1(name, type) {
      //console.log(name, type);
      ElMessageBox.confirm(
        '确定要删除吗?',
        '删除',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      )
        .then(() => {
          if (type == "file") {
            //console.log(this.nowPath.substring(1) + name);
            request.get("/file/deleteFile", {
              params: {
                "partPath": this.nowPath
              },
              headers: {
                "satoken": this.getCookie("satoken")
              }
            }).then(res => {
              if (res.code == 201) {
                return
              }
              if (res.code == 200) {
                this.$message({
                  message: '删除成功,一秒后跳回主页',
                  type: 'success'
                })
                setTimeout(() => {
                  this.$router.push('/home')
                }, 1000)
              } else {
                this.$message({
                  message: '删除失败',
                  type: 'error'
                })
                this.reflash()
              }
            }).catch(err => {
              console.log(err);
            })
          } else if (type = "folder") {
            request.get("/file/deleteFolder", {
              params: {
                "partPath": this.nowPath.substring(1) + name
              },
              headers: {
                "satoken": this.getCookie("satoken")
              }
            }).then(res => {
              if (res.code == 201) {
                return
              }
              if (res.code == 200) {
                this.$message({
                  message: '删除成功',
                  type: 'success'
                })
                this.reflash()
              } else {
                this.$message({
                  message: '删除失败',
                  type: 'error'
                })
                this.reflash()
              }
            }).catch(err => {
              console.log(err);
            })
          }
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    }, goParent() {
      window.location.href = "http://localhost:8080/home?path=/" + this.nowPath.substring(0, this.nowPath.lastIndexOf("/"))
    }, share() {
      request.post("/share/file", {
        "downloadUrl": window.location.href,
        "name": this.nowPath.substring(this.nowPath.lastIndexOf("/") + 1, this.nowPath.length),
      }, {
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
    },
    chat(){
      this.$router.push('/chat')
    },pdf2word(){
      request.get("/file/pdf2word", {
        params: {
          "partPath": this.nowPath
        },
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        if (res.code == 200) {
          ElMessage({
            type: 'success',
            message: '转换成功,已存放在同目录下',
          })
          setTimeout(() => {
            window.open("http://localhost:8080/home?path=/"+this.nowPath.substring(0,this.nowPath.lastIndexOf("/")))
          }, 1000)
        }
      }).catch(err => {
        console.log(err);
      })
    },
    word2pdf(){
      request.get("/file/word2pdf", {
        params: {
          "partPath": this.nowPath
        },
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        if (res.code == 200) {
          ElMessage({
            type: 'success',
            message: '转换成功,已存放在同目录下',
          })
          setTimeout(() => {
            window.open("http://localhost:8080/home?path=/"+this.nowPath.substring(0,this.nowPath.lastIndexOf("/")))
          }, 1000)
        }
      }).catch(err => {
        console.log(err);
      })
    },
    ocr1(){
      request.get("/file/ocr", {
        params: {
          "partPath": this.nowPath
        },
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        if (res.code == 200) {
          ElMessage({
            type: 'success',
            message: '识别完成',
          })
          this.ocr=res.data
        }
      }).catch(err => {
        console.log(err);
      })
    }
  }
}
</script>
