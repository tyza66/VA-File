<template>
  <div>
    <div class="header">
      <div class="header-part">
        <el-form-item label="搜索类型">
          <el-radio label="名称搜索" v-model="searchType" />
          <el-radio label="内容搜索" v-model="searchType" />
        </el-form-item>
      </div>
      <div class="header-part">
        <el-form-item label="搜索模式" class="header-part">
          <el-radio label="深度搜索" v-model="searchMode" :disabled="searchType=='名称搜索'"/>
          <el-radio label="快速搜索" v-model="searchMode" disabled />
        </el-form-item>
      </div>
      <div class="header-part">
        <el-form-item label="根路径" class="header-part">
          <el-input placeholder="请输入路径" v-model="partPath" />
        </el-form-item>
      </div>
      <div class="header-part">
        <el-form-item label="关键字" class="header-part">
          <el-input placeholder="请输入关键字" v-model="searchKey" />
        </el-form-item>
      </div>
      <el-button class="sb" type="default" @click="search()">搜索</el-button>
      <el-button class="rb" type="default" size="small">设置</el-button>
      <el-button class="rb" type="default" size="small">返回主页</el-button>
    </div>
    <div>
      <el-row class="item-one">
        <el-col  :span="1"></el-col>
      </el-row>
    </div>
  </div>
</template>

<style>
.header {
  height: 40px;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, .1);
  right: 0;
  z-index: 100;
}

.header:hover {
  background-color: #f4f4f4;
}

.header-part {
  display: inline-block;
  margin-left: 10px;
  margin-bottom: 0 !important;
  margin-top: 2px;
}

.el-form-item__label {
  font-family: "宋体";
  font-weight: bold;
}

.el-radio {
  margin-right: 15px !important;
}

.sb{
  margin-left: 5px;
  margin-top: -2px;
}

.rb{
  float: right;
  margin-left: 0px !important;
  margin-top: 8px;
  margin-right: 10px;
}

.item-one{
  margin-top: 10px;
  margin-left: 10px;
  margin-right: 10px;
  height: 40px;
  background-color: #fff;
  box-shadow: 0 1px 3px rgba(26, 26, 26, .1);
  border-radius: 5px;
  padding: 2px;
}

.item-one:hover {
  background-color: #f4f4f4;
}

.item-one el-col{
  height: 36px;
}
</style>

<script>
import { request } from '@/utils/request';

export default {
  name: 'SearchView',
  components: {
  },
  data() {
    return {
      searchType: '名称搜索',
      searchMode: '深度搜索',
      partPath: '/',
      searchKey: ''
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
    search(){
      console.log(this.searchType)
    }
  }
}
</script>
