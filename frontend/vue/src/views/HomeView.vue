<template>
  <div>
    <div class="header">
      <p>物理根路径>{{ basePath }}</p>
      <p>当前子路径>{{ nowPath }}</p>
      <el-button size="small" type="link" @click="set()">设置</el-button>
      <el-button size="small" type="link" @click="logout()">退出登录</el-button>
      <el-button size="small" type="link" @click="backRoom()">上级目录</el-button>
      <el-button size="small" type="link" @click="rootRoom()">回根目录</el-button>
      <el-button size="small" type="link" @click="uploadFile()">上传文件</el-button>
      <el-button size="small" type="link" @click="createFile()">新建文件</el-button>
      <el-button size="small" type="link" @click="createFolder()">建文件夹</el-button>
      <el-button size="small" type="link" @click="reflash()">刷新当前</el-button>
      <el-button size="small" type="link" @click="search()">检索工具</el-button>
    </div>
    <div class="main">
      <div class="file-and-folder" v-for="(item, index) in context" :key="index">
        <div class="img">
          <svg v-show="item.type == 'folder'" t="1690084461107" class="icon" viewBox="0 0 1024 1024" version="1.1"
            xmlns="http://www.w3.org/2000/svg" p-id="2324" width="200" height="200">
            <path
              d="M855.04 385.024q19.456 2.048 38.912 10.24t33.792 23.04 21.504 37.376 2.048 54.272q-2.048 8.192-8.192 40.448t-14.336 74.24-18.432 86.528-19.456 76.288q-5.12 18.432-14.848 37.888t-25.088 35.328-36.864 26.112-51.2 10.24l-567.296 0q-21.504 0-44.544-9.216t-42.496-26.112-31.744-40.96-12.288-53.76l0-439.296q0-62.464 33.792-97.792t95.232-35.328l503.808 0q22.528 0 46.592 8.704t43.52 24.064 31.744 35.84 12.288 44.032l0 11.264-53.248 0q-40.96 0-95.744-0.512t-116.736-0.512-115.712-0.512-92.672-0.512l-47.104 0q-26.624 0-41.472 16.896t-23.04 44.544q-8.192 29.696-18.432 62.976t-18.432 61.952q-10.24 33.792-20.48 65.536-2.048 8.192-2.048 13.312 0 17.408 11.776 29.184t29.184 11.776q31.744 0 43.008-39.936l54.272-198.656q133.12 1.024 243.712 1.024l286.72 0z"
              p-id="2325"></path>
          </svg>
          <svg v-show="item.type != 'folder'" t="1690087486861" class="icon" viewBox="0 0 1024 1024" version="1.1"
            xmlns="http://www.w3.org/2000/svg" p-id="1643" width="200" height="200">
            <path
              d="M682.666667 245.333333a10.666667 10.666667 0 0 0 10.666666 10.666667h189.913334c-0.913333-1.066667-1.86-2.12-2.866667-3.126667L685.793333 58.286667c-1.006667-1.006667-2.06-1.953333-3.126666-2.866667z"
              fill="#5C5C66" p-id="1644"></path>
            <path
              d="M640 245.333333V42.666667H181.333333a53.393333 53.393333 0 0 0-53.333333 53.333333v832a53.393333 53.393333 0 0 0 53.333333 53.333333h661.333334a53.393333 53.393333 0 0 0 53.333333-53.333333V298.666667h-202.666667a53.393333 53.393333 0 0 1-53.333333-53.333334z m-320 10.666667h170.666667a21.333333 21.333333 0 0 1 0 42.666667H320a21.333333 21.333333 0 0 1 0-42.666667z m384 512H320a21.333333 21.333333 0 0 1 0-42.666667h384a21.333333 21.333333 0 0 1 0 42.666667z m21.333333-234.666667a21.333333 21.333333 0 0 1-21.333333 21.333334H320a21.333333 21.333333 0 0 1 0-42.666667h384a21.333333 21.333333 0 0 1 21.333333 21.333333z"
              fill="#5C5C66" p-id="1645"></path>
          </svg>
        </div>
        <div class="info">
          <p>{{ (item.type == "folder") ? (item.name) : (item.name + "." + item.end) }}</p>
        </div>
        <div class="conf">
          <el-button size="small" link type="default" v-show="item.type == 'folder'"
            @click="openFolder(item.name)">打开</el-button>
          <el-button size="small" link type="default" v-show="item.type == 'file'">预览</el-button>
          <el-button size="small" link type="default" v-show="item.type != 'folder'"
            @click="download(item.name + '.' + item.end)">下载</el-button>
          <el-button size="small" link type="default" disabled v-show="item.type == 'folder'">下载</el-button>
          <el-button size="small" link type="default"
            @click="delete1((item.type == 'folder') ? (item.name) : (item.name + '.' + item.end), item.type)">删除</el-button>
          <el-button size="small" link type="default"
            @click="rename((item.type == 'folder') ? (item.name) : (item.name + '.' + item.end), item.type)">命名</el-button>
        </div>
      </div>
    </div>
    <el-dialog v-model="uploadShow" title="文件上传" width="30%" :before-close="handleClose">
      <div>
        上传路径：<input type="text" v-model="nowPath" disabled>
        <br />
        选择文件：<input type="file" id="file" name="file">
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="uploadClose()">取消</el-button>
          <el-button type="primary" @click="uplodatSubmit()">
            提交
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.header {
  width: 100%;
  height: 30px;
  background-color: aliceblue;
  line-height: 30px;
}

.header:hover {
  background-color: #e6f7ff;
}

.header p {
  display: inline-block;
  margin-left: 10px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.header button {
  display: inline-block;
  float: right;
  margin-right: 10px;
  margin-left: 0;
  height: 25px;
  margin-top: 2.5px;
}

.main {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  flex-wrap: wrap;
}

.file-and-folder {
  display: flex;
  flex-direction: column;
  width: 150px;
  height: 205px;
  padding: 10px;
  border: #eaeaea solid 1px;
  margin: 2px;
}

.file-and-folder .img {
  display: flex;
  justify-content: center;
  align-items: center;
}

.file-and-folder .img svg {
  width: 100px;
  height: 100px;
}

.file-and-folder .info {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-size: small;
  color: #606266;
  line-height: 15px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.file-and-folder .conf {
  display: flex;
  width: 100%;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}

.file-and-folder .conf button {
  display: inline-block;
  margin: 5px;
}

.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>

<script>
import { request, ref } from '@/utils/request';
import { ElMessageBox } from 'element-plus'

export default {
  name: 'HomeView',
  components: {

  },
  data() {
    return {
      user: null,
      basePath: "/",
      nowPath: "/",
      context: [],
      uploadShow: false
    }
  },
  created() {
    this.checkLogin()
    this.checkRoot()
    this.getRootContext()
    this.checkPath()
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
        } else {
          that.user = res.user
          //console.log(that.user);
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
    }, logout() {
      request.get('/user/logout', {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }
      ).then(res => {
        //console.log(res);
        if (res.code == 200) {
          this.$message({
            message: '退出登录成功,一秒后跳转回登录界面',
            type: 'success'
          })
          setTimeout(() => {
            this.$router.push('/login')
          }, 1000);
        }
      }).catch(err => {
        console.log(err);
      })
    }, set() {
      this.$router.push('/setting')
    }, checkRoot() {
      var that = this;
      request.get('/file/checkRoot', {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }
      ).then(res => {
        if (res.code == 201) {
          return
        }
        if (res.code == 200) {
          // this.$message({
          //   message: '物理文件根路径检查成功',
          //   type: 'success'
          // })
          request.get("/location/get", {
            headers: {
              "satoken": this.getCookie("satoken")
            }
          }).then(res => {
            if (res.code == 200) {
              that.basePath = res.data
            }
            //console.log(that.basePath);
          }).catch(err => {
            console.log(err);
          })
        } else {
          this.$message({
            message: '物理文件根路径检查失败，请前往设置中进行修改',
            type: 'error'
          })
        }
      }).catch(err => {
        console.log(err);
      })
    }, backRoom() {
      if (this.nowPath != "/") {
        this.nowPath = this.nowPath.substring(0, this.nowPath.lastIndexOf("/"))
        this.nowPath = this.nowPath.substring(0, this.nowPath.lastIndexOf("/")) + "/"
        this.reflash()
      } else {
        this.$message({
          message: '已经是根目录了',
          type: 'info'
        })
      }
    },
    rootRoom() {
      this.nowPath = "/"
      this.getRootContext()
      this.$message({
        message: '回到根目录成功',
        type: 'success'
      })
    },
    getRootContext() {
      var that = this
      request.get("/file/getIn", {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        if (res.code == 201) {
          return
        }
        if (res.code == 200) {
          that.context = res.data
          this.sortContent("type")
        }
      }).catch(err => {
        console.log(err);
      })
    },
    sortContent(way) {
      if (way == "type") {
        var temp = []
        for (var i in this.context) {
          if (this.context[i].type == "folder") {
            temp.push(this.context[i])
          }
        }
        for (var i in this.context) {
          if (this.context[i].type == "file") {
            temp.push(this.context[i])
          }
        }
        this.context = temp
      }
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
    },
    openFolder(name) {
      this.nowPath = this.nowPath + name + "/"
      var that = this
      request.get("/file/getIn", {
        headers: {
          "satoken": this.getCookie("satoken")
        }, params: {
          "partPath": this.nowPath.substring(1)
        }
      }).then(res => {
        if (res.code == 201) {
          return
        }
        if (res.code == 200) {
          that.context = res.data
          this.sortContent("type")
        }
      }).catch(err => {
        console.log(err);
      })
    }, reflash() {
      var that = this
      request.get("/file/getIn", {
        headers: {
          "satoken": this.getCookie("satoken")
        }, params: {
          "partPath": this.nowPath.substring(1)
        }
      }).then(res => {
        if (res.code == 201) {
          return
        }
        if (res.code == 200) {
          that.context = res.data
          this.sortContent("type")
          this.$message({
            message: '已刷新当前目录',
            type: 'success'
          })
        }
      }).catch(err => {
        console.log(err);
      })
    },
    rename(name, type) {
      //console.log(name, type);
      ElMessageBox.prompt('请输入文件/文件夹新的名称', '重命名', {
        confirmButtonText: '重命名',
        cancelButtonText: '取消',
      })
        .then(({ value }) => {
          if (type == "file") {
            request.get("/file/renameFile", {
              params: {
                "partPath": this.nowPath.substring(1) + name,
                "newName": value
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
                  type: 'success',
                  message: `重命名成功！新的名称:${value}`,
                })
                this.reflash()
              }
            }).catch(err => {
              console.log(err);
            })
          } else if (type == "folder") {
            request.get("/file/renameFolder", {
              params: {
                "partPath": this.nowPath.substring(1) + name,
                "newName": value
              },
              headers: {
                "satoken": this.getCookie("satoken")
              }
            }).then(res => {
              console.log(res);
              if (res.code == 201) {
                return
              }
              if (res.code == 200) {
                this.$message({
                  type: 'success',
                  message: `重命名成功！新的名称:${value}`,
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
            message: '已取消重命名',
          })
        })
    }, download(name) {
      var path = "http://localhost:9090/file/download.action?partPath=" + this.nowPath.substring(1) + name
      //console.log(path);
      window.open(path)
    }, createFolder() {
      ElMessageBox.prompt('请输入要新建文件夹的名称', '新建文件夹', {
        confirmButtonText: '创建',
        cancelButtonText: '取消',
      })
        .then(({ value }) => {
          request.get("/file/createFolder", {
            params: {
              "partPath": this.nowPath.substring(1) + value
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
                type: 'success',
                message: `新建文件夹成功！名称为:${value}`,
              })
              this.reflash()
            }
          }).catch(err => {
            console.log(err);
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消创建文件夹',
          })
        })
    }, createFile() {
      ElMessageBox.prompt('请输入要新建文件的名称', '新建文件', {
        confirmButtonText: '创建',
        cancelButtonText: '取消',
      })
        .then(({ value }) => {
          request.get("/file/createFile", {
            params: {
              "partPath": this.nowPath.substring(1) + value
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
                type: 'success',
                message: `新建文件成功！名称为:${value}`,
              })
              this.reflash()
            }
          }).catch(err => {
            console.log(err);
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消创建文件',
          })
        })
    }, uploadFile() {
      this.uploadShow = true
    }, handleClose() {
      ElMessageBox.confirm('确定要关闭本窗口吗')
        .then(() => {
          this.uploadShow = false
          done()
        })
        .catch(() => {
          // catch error
        })
    }, uploadClose() {
      this.uploadShow = false;
      this.$message({
        type: 'info',
        message: '已取消上传文件',
      })
    },
    uplodatSubmit() {
      const file = document.querySelector('input[type=file]').files[0]
      const formData = new FormData()
      formData.append('file', file)
      formData.append('partPath', this.nowPath.substring(1))
      request.post("/file/upload.action", formData, {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(
        res => {
          if (res.code == 201) {
            return
          }
          if (res.code == 200) {
            this.$message({
              type: 'success',
              message: `上传文件成功！`,
            })
            this.reflash()
            this.uploadShow = false;
          }
        }
      ).catch(error => {
        console.log(error);
      })
    }, search() {
      this.$router.push('/search')
    }, getQueryVariable(variable) {
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
    }, checkPath() {
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
    }
  }
}
</script>
