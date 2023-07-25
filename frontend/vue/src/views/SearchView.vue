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
          <el-radio label="深度搜索" v-model="searchMode" :disabled="searchType == '名称搜索'" />
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
      <el-button class="rb" type="default" size="small" @click="goSetting()">设置</el-button>
      <el-button class="rb" type="default" size="small" @click="goHome()">返回主页</el-button>
    </div>
    <div class="items">
      <el-row class="item-one item-title">
        <el-col class="icon" :span="1">
          图标
        </el-col>
        <el-col class="type" :span="2">
          类型
        </el-col>
        <el-col class="name" :span="5">
          名称
        </el-col>
        <el-col class="size" :span="3">
          大小
        </el-col>
        <el-col class="path" :span="5">
          路径
        </el-col>
        <el-col class="control" :span="8">
          操作
        </el-col>
      </el-row>
      <el-row class="item-one" v-for="(item,index) in context" :key="index">
        <el-col class="icon" :span="1">
          <svg v-show="item.type == 'folder'" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2324" width="30"
            height="30">
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
        </el-col>
        <el-col class="type" :span="2">
          {{ item.type == 'file' ? '文件' : '文件夹'  }}
        </el-col>
        <el-col class="name" :span="5">
          {{ item.name + (item.type=="file"?("."+ item.end):"") }}
        </el-col>
        <el-col class="size" :span="3">
          {{ item.size  }}
        </el-col>
        <el-col class="path" :span="5">
          {{ item.prototype }}
        </el-col>
        <el-col class="control" :span="8">
          <el-button type="default" size="small" v-show="item.type =='file'">预览</el-button>
          <el-button type="default" size="small" v-show="item.type =='folder'">打开</el-button>
          <el-button type="default" size="small">前往</el-button>
          <el-button type="default" size="small">删除</el-button>
          <el-button type="default" size="small">命名</el-button>
          <el-button type="default" size="small" v-show="item.type =='file'">下载</el-button>
          <el-button type="default" size="small" v-show="item.type =='folder'">设为根路径</el-button>
        </el-col>
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

.sb {
  margin-left: 5px;
  margin-top: -2px;
}

.rb {
  float: right;
  margin-left: 0px !important;
  margin-top: 8px;
  margin-right: 10px;
}

.items {
  display: flex;
  flex-direction: column;
}

.item-one {
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

.item-one el-col {
  height: 36px;
}

.item-one .icon,
.item-one .type,
.item-one .name,
.item-one .size,
.item-one .path,
.item-one .control {
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.item-one .icon svg {
  height: 30px;
  widows: 30px;
}

.item-title {
  border: #9c9c9c solid 1px;
}

.item-one .control {
  flex-direction: row;
  justify-content: center;
  align-items: center;
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
      searchKey: '',
      context: [],
      basePath: "/",
    }
  },
  created() {
    this.checkLogin()
    this.checkRoot()
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
    search() {
      //console.log(this.searchType)
      if (this.searchType == '名称搜索') {
        request.get('/search/byName', {
          params: {
            "partPath": this.partPath,
            "key": this.searchKey
          },
          headers: {
            "satoken": this.getCookie("satoken")
          }
        }
        ).then(res => {
          //console.log(res);
          if (res.code == 200) {
            this.context = res.data
            this.sortContent("type")
          }
        }).catch(err => {
          console.log(err);
        })
      } else if(this.searchType == '内容搜索') {
        request.get('/search/byContent', {
          params: {
            "partPath": this.partPath,
            "key": this.searchKey
          },
          headers: {
            "satoken": this.getCookie("satoken")
          }
        }
        ).then(res => {
          //console.log(res);
          if (res.code == 200) {
            this.context = res.data
          }
        }).catch(err => {
          console.log(err);
        })
      }
    },
    goSetting() {
      this.$router.push('/setting')
    },
    goHome() {
      this.$router.push('/home')
    },sortContent(way) {
      if (way == "type") {
        var temp = []
        for (var i in this.context) {
          console.log(this.basePath)
          //将地址位置格式化
          this.context[i].prototype = this.context[i].prototype.replaceAll("\\","/")
          this.context[i].prototype = this.context[i].prototype.replace(this.basePath,"")
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
    },checkRoot() {
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
    } 
  }
}
</script>
