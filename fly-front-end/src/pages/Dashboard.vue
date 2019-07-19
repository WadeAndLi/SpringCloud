<template>
  <v-container fluid grid-list-md>
    <v-layout row wrap>
      <v-flex xs10 md6>
        <v-card>
          <v-card-text class="px2">
            <div ref="sale" style="width: 100%;height:350px"></div>
          </v-card-text>
        </v-card>
      </v-flex>

      <v-flex xs10 md6>
        <v-card >
          <v-card-text class="px2">
            <div ref="pie" style="width: 100%;height:350px"></div>
          </v-card-text>
        </v-card>
      </v-flex>
    </v-layout>
    <button class="socket-button" @click="openSocket">点击打开Socket</button>
    <div>{{ message }}</div>
    <button class="socket-button" @click="sendMessage"></button>
    <button class="socket-button" @click="closeSocket">点击关闭Socket</button>
  </v-container>
</template>

<script>
  // 引入 ECharts 主模块
  var echarts = require('echarts/lib/echarts');
  require('echarts/lib/chart/bar');
  require('echarts/lib/chart/pie');
  import Stomp from 'stompjs'
  import SockJS from "sockjs-client"

  export default {
    name: "dashboard",
    data(){
      return {
        message: "正在连接Websocket...",
        socket: {},
        socketClient: null,
      }
    },
    mounted(){
      this.$nextTick(() => {
        var sale = echarts.init(this.$refs.sale);

        // 指定图表的配置项和数据
        var option = {
          title: {
            text: '销售统计'
          },
          tooltip: {},
          legend: {
            data:['销量']
          },
          xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
          },
          yAxis: {},
          series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
          }]
        };

        // 使用刚指定的配置项和数据显示图表。
        sale.setOption(option);

        const pie = echarts.init(this.$refs.pie);

        pie.setOption({
          roseType: 'angle',
          title: {
            text: '访问来源'
          },
          series : [
            {
              name: '访问来源',
              type: 'pie',
              radius: '55%',
              data:[
                {value:235, name:'视频广告'},
                {value:274, name:'联盟广告'},
                {value:310, name:'邮件营销'},
                {value:335, name:'直接访问'},
                {value:400, name:'搜索引擎'}
              ]
            }
          ],
          itemStyle: {
            emphasis: {
              // 阴影的大小
              shadowBlur: 200,
              // 阴影水平方向上的偏移
              shadowOffsetX: 0,
              // 阴影垂直方向上的偏移
              shadowOffsetY: 0,
              // 阴影颜色
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        })
      })
    },
    methods: {
      openSocket: function() {
        if (this.socketClient === null) {
          this.message = "正在启动websocket...";
          this.socket = new SockJS('http://localhost:8018/websocket');
          this.socketClient = Stomp.over(this.socket);
          this.socketClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            this.socketClient.subscribe('/topic/message', function(response) {
                this.message = response;
            })
          })
        }
      },
      closeSocket: function() {
        if (this.socketClient != null) {
          this.socketClient.disconnect();
          this.socketClient = null;
          this.message = "Socket连接已经关闭";
        }
      },
      sendMessage: function() {
        this.socketClient.send("/sendTest", {}, "Good");
      }
    }
  }
</script>

<style scoped>
  .socket-button {
    padding: 5px 18px;
    background-color: aqua;
    border-radius: 5px;
    color: white;
  }
</style>
