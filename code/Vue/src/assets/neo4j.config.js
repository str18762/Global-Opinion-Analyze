export default {
    containerId: 'viz',
    neo4j: {
        serverUrl: 'bolt://localhost:7687',
        serverUser: 'neo4j',
        serverPassword: '12345678'
    },
    labels: {
        // 可以配置样式 但是我设置无效，有知道怎么设置的小伙伴吗
        // 食物图数据节点
        "GovernmentCertified": {
            label: 'name',
            font: {size: 25}, // 节点字体大小
            size: 200,           // 节点大小
            color: '#EE2233'    // 节点颜色
        },
        "MediaCertified": {
            label: 'name',
            font: {size: 23}, // 节点字体大小
            size: 150,           // 节点大小
            color:'#331155'    // 节点颜色
        },
        "PersonalCertified": {
            label: 'followers',
            font: {size: 20}, // 节点字体大小
            size: 100,           // 节点大小
        },
        "Unclassified": {
            label: 'name',
            font: {size: 17}, // 节点字体大小
            size: 50,           // 节点大小
            color: '#3333ee'    // 节点颜色
        },
    },
    relationships: {
        "关注": {
            value: "weight",
            label: "relation",

        }
    },

}