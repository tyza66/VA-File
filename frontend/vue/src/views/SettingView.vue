<template>
  <div>
    <div class="header"></div>
    <div class="main">
      <h1>VAF设置</h1>
      <div class="options">
        <el-form label-width="120px">
          <el-form-item label="文件树根路径：">
            <el-input v-model="rootPath"></el-input>
            <el-button type="default" @click="getRoot()">刷新</el-button>
            <el-button type="default" @click="clearRoot()">清空</el-button>
            <el-button type="default" @click="setRoot()">提交</el-button>
          </el-form-item>
          <el-form-item label="修改当前用户：">
            新密码：<el-input v-model="newPassword"></el-input>
            动态码：<el-input v-model="proxyCode1"></el-input>
            <el-button type="default" @click="clearNowUser()">清空</el-button>
            <el-button type="default" @click="updatePassword()">提交</el-button>
          </el-form-item>
          <el-form-item label="新增内部用户：">
            账号：<el-input v-model="newUserName"></el-input>
            密码：<el-input v-model="newUserPassword"></el-input>
            动态码：<el-input v-model="proxyCode2"></el-input>
            <el-button type="default" @click="clearNewUser()">清空</el-button>
            <el-button type="default" @click="createNewUser()">提交</el-button>
          </el-form-item>
          <el-form-item label="禁用用户：">
            用户名：<el-input v-model="disableName"></el-input>
            动态码：<el-input v-model="proxyCode3"></el-input>
            <el-button type="default" @click="clearDisable()">清空</el-button>
            <el-button type="default" @click="disableUser()">提交</el-button>
          </el-form-item>
          <el-form-item label="解禁用户：">
            用户名：<el-input v-model="enableName"></el-input>
            动态码：<el-input v-model="proxyCode4"></el-input>
            <el-button type="default" @click="clearEnable()">清空</el-button>
            <el-button type="default" @click="enableUser()">提交</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style>
.mian {
  widows: 100%;
}

.main h1 {
  text-align: center;
  margin: 10px
}

.options {
  margin: 0 auto;
}

.options form {
  width: 50%;
  margin: 0 auto;
}

.options form button {
  margin-top: 10px;
}
</style>


<script>
import { request } from '@/utils/request';

export default {
  name: 'SettingView',
  components: {
  },
  data() {
    return {
      rootPath: '/',
      newPassword: '',
      proxyCode1: '',
      newUserName: '',
      newUserPassword: '',
      proxyCode2: '',
      disableName: '',
      proxyCode3: '',
      enableName: '',
      proxyCode4: ''
    }
  },
  created() {
    this.checkLogin()
    this.getRoot()
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
    },
    getRoot() {
      request.get("/location/get", {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        //console.log(res);
        if (res.code == 200) {
          this.rootPath = res.data
        }
      }).catch(err => {
        console.log(err);
      })
    }, clearRoot() {
      this.rootPath = '';
    }, setRoot() {
      request.post("/location/set", {
        "vfmlLocation": this.rootPath
      }, {
        headers: {
          "satoken": this.getCookie("satoken")
        }
      }).then(res => {
        if (res.code == 201) {
          return
        }
        if (res.code == 200) {
          this.$message({
            message: '设置成功',
            type: 'success'
          });
        }
      }).catch(err => {
        console.log(err);
      })
    },
    clearNowUser() {
      this.newPassword = '';
      this.proxyCode1 = '';
    },
    updatePassword() {
      this.$confirm('确认修改密码？').then(() => {
        if (this.proxyCode1 == "96") {
          request.post("/user/changePassword", {
            "vfmuPassword": this.newPassword
          }, {
            headers: {
              "satoken": this.getCookie("satoken")
            }
          }).then(res => {
            if (res.cede == 201) {
              return
            }
            if (res.code == 200) {
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

            }
          }).catch(err => {
            console.log(err);
          })
        } else {
          this.$message({
            message: '动态码错误',
            type: 'error'
          });
        }
      })
    }, clearNewUser() {
      this.newUserName = '';
      this.newUserPassword = '';
      this.proxyCode2 = '';
    }, createNewUser() {
      this.$confirm('确认创建新用户？').then(() => {
        //为空验证
        if (this.newUserName == '' || this.newUserPassword == '' || this.proxyCode2 == '') {
          this.$message({
            message: '请填写完整',
            type: 'error'
          });
          return
        }
        if (this.proxyCode2 == "96") {
          request.post("/user/register", {
            "vfmuUsername": this.newUserName,
            "vfmuPassword": this.newUserPassword
          }, {
            headers: {
              "satoken": this.getCookie("satoken")
            }
          }).then(res => {
            if (res.cede == 201) {
              return
            }
            if (res.code == 200) {
              this.$message({
                message: '创建成功',
                type: 'success'
              });
              this.clearNewUser();
            }
            if (res.code == 199) {
              this.$message({
                message: '创建失败，用户名已存在',
                type: 'error'
              });
            }
          }).catch(err => {
            console.log(err);
          })
        } else {
          this.$message({
            message: '动态码错误',
            type: 'error'
          });
        }
      })
    },
    clearDisable() {
      this.disableName = '';
      this.proxyCode3 = '';
    },
    clearEnable() {
      this.enableName = '';
      this.proxyCode4 = '';
    },
    disableUser() {
      this.$confirm('确认禁用用户？').then(() => {
        //为空验证
        if (this.disableName == '' || this.proxyCode3 == '') {
          this.$message({
            message: '请填写完整',
            type: 'error'
          });
          return
        }
        if (this.proxyCode3 == "96") {
          request.get("/user/disableUser", {
            params: {
              "username": this.disableName
            },
            headers: {
              "satoken": this.getCookie("satoken")
            }
          }).then(res => {
            if (res.cede == 201) {
              return
            }
            if (res.code == 200) {
              this.$message({
                message: '禁用成功',
                type: 'success'
              });
              this.clearDisable();
            }
            if (res.code == 199) {
              this.$message({
                message: '禁用失败，用户名不存在',
                type: 'error'
              });
            }
          }).catch(err => {
            console.log(err);
          })
        } else {
          this.$message({
            message: '动态码错误',
            type: 'error'
          });
        }
      })
    },
    enableUser() {
      this.$confirm('确认解禁用户？').then(() => {
        //为空验证
        if (this.enableName == '' || this.proxyCode4 == '') {
          this.$message({
            message: '请填写完整',
            type: 'error'
          });
          return
        }
        if (this.proxyCode4 == "96") {
          request.get("/user/enableUser", {
            params: {
              "username": this.enableName
            },
            headers: {
              "satoken": this.getCookie("satoken")
            }
          }).then(res => {
            if (res.cede == 201) {
              return
            }
            if (res.code == 200) {
              this.$message({
                message: '解禁成功',
                type: 'success'
              });
              this.clearEnable();
            }
            if (res.code == 199) {
              this.$message({
                message: '解禁失败，用户名不存在',
                type: 'error'
              });
            }
          }).catch(err => {
            console.log(err);
          })
        } else {
          this.$message({
            message: '动态码错误',
            type: 'error'
          });
        }
      })
    }
  }
}
</script>
