<template>
  <d2-container>
    <template slot="header">
      <el-row :gutter="20" type="flex" justify="space-between">
        <el-col :span="2.5">
          <!-- 顶部两个按钮-管理员 -->
          <el-button-group v-if="userAdministratorPermissions">
            <!-- 顶部两个按钮-其一 -->
            <el-tooltip
              content="查看新物品购置资金请求"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="drawerNewItemArouseChangesNumber++">
                申请信息
              </el-button>
            </el-tooltip>
            <!-- 顶部两个按钮-其二 -->
            <el-tooltip
              content="查看物品损坏信息"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="drawerOldItemArouseChangesNumber++">
                损坏信息
              </el-button>
            </el-tooltip>
          </el-button-group>
          <!-- 顶部两个按钮-普通用户 -->
          <el-button-group v-if="!userAdministratorPermissions">
            <!-- 顶部两个按钮-其一 -->
            <el-tooltip
              content="新物品购置资金申请"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="dialogNewItemArouseChangesNumber++">
                物品申请
              </el-button>
            </el-tooltip>
            <!-- 顶部两个按钮-其二 -->
            <el-tooltip
              content="物品损坏上报"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="dialogOldItemArouseChangesNumber++">
                损坏上报
              </el-button>
            </el-tooltip>
          </el-button-group>
        </el-col>
        <el-col :span="9">
          <el-input
            ref="inputSearch"
            v-model="inputSearch"
            placeholder="请输入搜索内容"
            @change="handleCurrentChange(1)"
            @keyup.enter.native="searchEnter('inputSearch')"
            @focus="inputFocus('inputSearch')">
          </el-input>
        </el-col>
        <el-col :span="2.5">
          <el-tooltip
            v-if="userAdministratorPermissions"
            content="添加物品 | 很简单对吧?"
            placement="bottom">
            <el-button
              type="primary"
              size="medium"
              @click="addNewItemArouseChangesNumber++">
              添加新物品
            </el-button>
          </el-tooltip>
          <el-tooltip
            v-if="!userAdministratorPermissions"
            content="看看你以前上报了什么??"
            placement="bottom">
            <el-button-group>
              <el-button
                type="primary"
                size="medium"
                @click="drawerNewItemArouseChangesNumber++">
                物品申请历史
              </el-button>
              <el-button
                type="primary"
                size="medium"
                @click="drawerOldItemArouseChangesNumber++">
                损坏上报历史
              </el-button>
            </el-button-group>
          </el-tooltip>
        </el-col>
      </el-row>
    </template>

    <!-- 表格 -->
    <itemTable
      :tableData="tableData"
      :randomKey="randomKey"
      :loadAnimation="loadAnimation"
      @dialogOldItem="dialogOldItem"
      @deleteInformation="deleteInformation"
      @dialogChangeItem="dialogChangeItem"
      @dialogVideoPlayerArouse="dialogVideoPlayerArouse"/>

    <!-- 底部分页 -->
    <template slot="footer">
      <el-row :gutter="20" type="flex" class="row-bg" justify="space-around">
        <el-col :span="12">
          <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="pagination.currentPage"
              :page-size="pagination.pageSize"
              :page-sizes="[5, 10, 20, 30]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="pagination.allData">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </template>
    <!-- 顶部两个按钮其一换出的 drawer -->
    <showNewItem :drawerArouse="drawerNewItemArouseChangesNumber"/>
    <!-- 顶部两个按钮其二换出的 drawer -->
    <showDamageItem :drawerArouse="drawerOldItemArouseChangesNumber"/>
    <!-- 顶部两个按钮其一换出的 dialog -->
    <applyNewItem :dialogArouse="dialogNewItemArouseChangesNumber"/>
    <!-- 顶部两个按钮其二换出的 dialog -->
    <damageItemReport :dialogArouse="dialogOldItemArouseChangesNumber" :oldItemId_Name="oldItemId_Name"/>
    <!-- 右侧管理员按钮 -->
    <addNewItem :dialogArouse="addNewItemArouseChangesNumber" @addSuccess="addSuccess"/>
    <!-- 列表管理员编辑按钮 -->
    <changeItem :dialogArouse="dialogChangeItemArouseChangesNumber" :changeItemInformation="changeItemInformation" @changeItemSuccess="changeItemSuccess" @dialogVideoPlayerArouse="dialogVideoPlayerArouse"/>
    <!-- 视频播放器 -->
    <videoPlayer :dialogVideoPlayer="dialogVideoPlayer" :videoUrl="videoUrl"/>
  </d2-container>
</template>

<script>
import util from '@/libs/util'
import api from '@/api'
import showNewItem from './components/showNewItem'
import showDamageItem from './components/showDamageItem'
import applyNewItem from './components/applyNewItem'
import damageItemReport from './components/damageItemReport'
import addNewItem from './components/addNewItem'
import changeItem from './components/changeItem'
import itemTable from './components/itemTable'
import videoPlayer from './components/videoPlayer'
export default {
  name: 'fixedAssetManagement-itemManagement',
  components: {
    showNewItem,
    showDamageItem,
    applyNewItem,
    damageItemReport,
    addNewItem,
    changeItem,
    itemTable,
    videoPlayer
  },
  data () {
    return {
      tableData: [],
      // 随机key,用于表格强制刷新
      randomKey: 0,
      drawerNewItemArouseChangesNumber: 0,
      drawerOldItemArouseChangesNumber: 0,
      dialogNewItemArouseChangesNumber: 0,
      dialogOldItemArouseChangesNumber: 0,
      oldItemId_Name: {},
      addNewItemArouseChangesNumber: 0,
      dialogChangeItemArouseChangesNumber: 0,
      dialogVideoPlayer: 0,
      videoUrl: '',
      changeItemInformation: {},
      // 搜索栏的值
      inputSearch: '',
      // 分页数据
      pagination: {
        // 当前页
        currentPage: 1,
        // 每页数量
        pageSize: 10,
        // 总页
        allPage: 1,
        // 总数据量
        allData: 1
      },
      // 用于启动表格加载动画
      loadAnimation: true,
      // 0 为 true 是管理员
      // 1 为 false 非管理员
      userAdministratorPermissions: util.cookies.get('userStatus') === '0' || false
    }
  },
  methods: {
    /**
     * @description 分页每页数量改变时触发
     * @param {Number} val 当前pageSize
     */
    handleSizeChange (val) {
      this.pagination.pageSize = val
      this.pagination.currentPage = 1
      this.itemSearch({
        page: 1,
        pageSize: this.pagination.pageSize,
        search: this.inputSearch
      })
    },
    /**
     * @description 分页页码改变时触发
     * @param {Number} val 当前page
     */
    handleCurrentChange (val) {
      this.pagination.currentPage = val
      this.itemSearch({
        page: val,
        pageSize: this.pagination.pageSize,
        search: this.inputSearch
      })
    },
    /**
     * @description 搜索框聚焦触发
     * @param {String} that 输入框ref
     */
    inputFocus (that) {
      this.$refs[that].select()
    },
    /**
     * @description 搜索框回车触发
     * @param {String} that 输入框ref
     */
    searchEnter (that) {
      this.$refs[that].blur()
    },
    async showImgAPI (url) {
      return await api.SHOW_IMG_API(url)
    },
    /**
     * @description 物品查询请求api
     * @param {object} page // Number 分页页码
     * @param {object} pageSize // Number 分页每页数量
     * @param {object} search // String 检索内容
     */
    async itemSearchAPI ({ page, pageSize, search }) {
      return await api.ITEM_SEARCH_API({ page, pageSize, search })
    },
    /**
     * @description 物品查询请求
     * @param {object} page // Number 分页页码
     * @param {object} pageSize // Number 分页每页数量
     * @param {object} search // String 检索内容
     */
    itemSearch ({ page = 1, pageSize = 10, search }) {
      this.itemSearchAPI({ page, pageSize, search })
        .then(v => {
          if (v === 'fail') {
            this.loadAnimation = false
            return this.$message.error('获取失败')
          } else if (v.status >= 40000) {
            this.$log.push({
              message: '错误代码:' + v.status + ',' + v.message,
              type: 'warning'
            })
            return this.$notify({
              title: v.message,
              message: '错误代码:' + v.status,
              position: 'bottom-left',
              type: 'warning'
            })
          }
          this.tableData = JSON.parse(JSON.stringify(v.items))
          for (let i = 0; i < this.tableData.length; i++) {
            this.tableData[i].img = ''
            if (this.tableData[i].imgs !== '') {
              this.tableData[i].urlSave = this.tableData[i].imgs
              this.showImgAPI(this.tableData[i].imgs)
                .then(v => {
                  this.tableData[i].imgs = v
                })
            } else {
              this.tableData[i].urlSave = this.tableData[i].videos
              this.showImgAPI(this.tableData[i].videos)
                .then(v => {
                  this.tableData[i].videos = v
                })
            }
          }
          this.pagination.allPage = v.totalPage
          this.pagination.allData = v.total
          this.loadAnimation = false
        })
    },
    /**
     * @description 唤起 dialog 旧物品损坏上报
     */
    dialogOldItem ({ itemId, itemName, number }, arouse) {
      this.oldItemId_Name = { itemId, itemName, number }
      if (arouse) this.dialogOldItemArouseChangesNumber++
    },
    /**
     * @description 唤起 dialog 修改物品
     * @param {Object} row
     */
    dialogChangeItem (row) {
      this.changeItemInformation = row
      this.dialogChangeItemArouseChangesNumber++
    },
    /**
     * @description 修改成功,返回修改表格
     * @param {Object} row
     */
    changeItemSuccess (row) {
      const data = this.tableData
      let newIndex = null
      this.tableData.map((item, index) => {
        if (item.itemId === row.itemId) {
          data[index] = row
          newIndex = index
        }
      })
      if (row.imgs.split('/')[0] === 'http:' || row.imgs.split('/')[0] === 'https:' || row.videos.split('/')[0] === 'http:' || row.videos.split('/')[0] === 'https:') {
        if (data[newIndex].imgs !== '') {
          this.showImgAPI(data[newIndex].imgs)
            .then(v => {
              data[newIndex].imgs = v
              this.tableData = data
            })
        } else {
          this.showImgAPI(data[newIndex].videos)
            .then(v => {
              data[newIndex].videos = v
              this.tableData = data
            })
        }
      }
      // setTimeout(() => {
      //   this.randomKey = Math.random()
      // }, 100)
      this.randomKey = Math.random()
    },
    /**
     * @description 删除成功事件
     * @param {Boolean} state 是否删除成功
     */
    deleteInformation (state) {
      if (!state) return 0
      this.loadAnimation = true
      clearTimeout(this.timeout)
      this.timeout = setTimeout(() => {
        this.itemSearch({
          page: this.pagination.currentPage,
          pageSize: this.pagination.pageSize,
          search: this.inputSearch
        })
      }, 10)
    },
    /**
     * @description 启动视频播放器
     * @param {String} url 视频链接
     */
    dialogVideoPlayerArouse (url) {
      this.videoUrl = url
      this.dialogVideoPlayer++
    },
    /**
     * @description 物品增加成功触发
     */
    addSuccess () {
      this.itemSearch({
        page: this.pagination.currentPage,
        pageSize: this.pagination.pageSize,
        search: this.inputSearch
      })
    }
  },
  mounted () {
    this.itemSearch({
      page: 1,
      pageSize: 10,
      search: ''
    })
  }
}
</script>

<style scoped less="">
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
    margin-bottom: 0;
  }
.el-col {
  border-radius: 4px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc00;
}
.block {
  border-radius: 3px;
}
</style>
