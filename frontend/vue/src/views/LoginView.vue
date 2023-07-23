<template>
  <div>
    <div class="login-header">
      <h1>VA-文件系统管理员登录</h1>
    </div>
    <div class="login-body">
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password"></el-input>
        </el-form-item>
        <el-form-item label="动态码" prop="proxyCode">
          <el-input v-model="form.proxyCode"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
.login-header {
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
}

.login-body {
  width: 50%;
  margin: 0 auto;
}
</style>

<script>
import { request } from '@/utils/request';
import { ElMessage } from 'element-plus'

export default {
  name: 'LoginView',
  components: {

  },
  data() {
    return {
      form: {
        username: '',
        password: '',
        proxyCode: ''
      }
    }
  },
  created() {
    this.checkLogin()
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
        if (res.code == 200) {
          this.$router.push('/home')
        }
      }).catch(err => {
        console.log(err);
      })
    },
    login() {
      if (this.form.username == '') {
        ElMessage.error('请输入用户名')
        return
      }
      if (this.form.password == '') {
        ElMessage.error('请输入密码')
        return
      }
      if (this.form.proxyCode == '') {
        ElMessage.error('请输入动态码')
        return
      }
      if (this.form.proxyCode != 96) {
        ElMessage({
          message: '状态码不正确',
          type: 'error'
        })
        return
      }
      request.post('/user/login',
        {
          "vfmuUsername": this.form.username,
          "vfmuPassword": this.form.password
        }).then(res => {
          if (res.code == 200) {
            this.setCookie("satoken", res.token);
            ElMessage({
              message: '登录成功,1秒后跳转',
              type: 'success'
            })
            setInterval(
              () => {
                this.$router.push('/home')
              }, 1000)
          } else {
            ElMessage({
              message: res.msg,
              type: 'error'
            })
          }
        }).catch(err => {
          console.log(err);
        })
    }, setCookie(key, value) {
      // 构建新的cookie字符串  
      var cookie = key + '=' + value + ';';
      // 将新cookie字符串添加到现有的cookie字符串中  
      var existingCookies = document.cookie.split(';');
      for (var i = 0; i < existingCookies.length; i++) {
        if (existingCookies[i].trim().indexOf(key) === 0) {
          // 如果找到指定的cookie键，则用新的值替换它  
          existingCookies[i] = cookie;
          break;
        }
      }
      //如果不存在就在后面直接拼接
      if (i == existingCookies.length) {
        existingCookies.push(cookie)
      }
      // 将修改后的cookie字符串重新组合并设置回document.cookie属性  
      document.cookie = existingCookies.join('');
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
    }
  }
}
</script>
