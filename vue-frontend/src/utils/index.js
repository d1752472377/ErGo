
/**
 * [Datestr 时间戳转字符串格式]
 */
export function socialDateFormat(data){
    //获取js时间
    var time = new Date().getTime()
    //去掉js时间戳后三位
    time = parseInt((time-data)/1000)
    //存储转换值
    var s
    if (time < 60*10) {
        return "刚刚"
    }else if((time < 60*60) && (time >= 60*10)){
        s = Math.floor(time/60)
        return s + '分钟前'
    }else if ((time < 60 * 60 * 24) && (time >= 60 * 60)) {
        // 超过1小时少于24小时
        s = Math.floor(time / 60 / 60)
        return s + '小时前'
    }else if ((time < 60 * 60 * 24 * 3) && (time >= 60 * 60 * 24)) {
        // 超过1天少于3天内
        s = Math.floor(time / 60 / 60 / 24)
        return s + '天前'
    } else {
        // 超过3天
        date = new Date(parseInt(date))
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()
    }
}

/**
 * 映射tag颜色
 */
export function mapTagColor(index) {
    switch (index % 11) {
        case 0:
            return '#D52BB3'
        case 1:
            return '#3CC48D'
        case 2:
            return '#d81e06'
        case 3:
            return '#1296db'
        case 4:
            return '#13227a'
        case 5:
            return '#3cc2a8'
        case 6:
            return '#1cedf2'
        case 7:
            return '#6b1cf2'
        case 8:
            return '#e71cf2'
        case 9:
            return '#f28a1c'
        case 10:
            return '#f4ea2a'
    }
}
/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
// 定义 treeDataTranslate 函数，接受三个参数：data, id, pid
export function treeDataTranslate(data, id = 'id', pid = 'parentId') {
    // 声明一个空数组 res，用于存储最终的嵌套树形结构数据
    var res = []
    // 声明一个空对象 temp，用于临时存储数据，以便在转换过程中快速查找
    var temp = {}
    // 使用 for 循环遍历输入数据 data，将每个数据对象存储到 temp 对象中，以数据对象的 id 属性作为键
    for (var i = 0; i< data.length; i++) {
        temp[data[i][id]] = data[i]
    }
    // 再次使用 for 循环遍历输入数据 data
    for (var k = 0; k< data.length; k++) {
        // 检查当前数据对象的 pid 属性是否存在于 temp 对象中，并且数据对象的 id 属性不等于其 pid 属性
        if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
            // 如果父节点不存在 children 属性，则创建一个空数组
            if (!temp[data[k][pid]]['children']) {
                temp[data[k][pid]]['children'] = []
            }
            // 如果父节点不存在 _level 属性，则为其设置一个默认值 _level 为 1
            if (!temp[data[k][pid]]['_level']) {
                temp[data[k][pid]]['_level'] = 1
            }
            // 将当前数据对象的 _level 属性设置为父节点的 _level 属性加 1
            data[k]['_level'] = temp[data[k][pid]]._level + 1
            // 将当前数据对象添加到父节点的 children 数组中
            temp[data[k][pid]]['children'].push(data[k])
        } else {
            // 如果数据对象没有父节点，则将其添加到 res 数组中
            res.push(data[k])
        }
    }
    // 返回 res 数组，其中包含嵌套的树形结构数据
    return res
}


export const mixin = {
    // 定义 filters 对象，包含多个过滤器函数
    filters: {
        // 创建一个名为 mapTagColor 的过滤器，用于映射标签颜色
        mapTagColor: function(index) {
            return mapTagColor(index)
        },
        // 创建一个名为 socialDate 的过滤器，用于格式化时间
        socialDate: function(formatedDate) {
            return socialDateFormat(formatedDate)
        },
        // 创建一个名为 filterHtml 的过滤器，用于去除 HTML 标签
        filterHtml: function(richText) {
            return richText.replace(/<.+?>/g, '')
        },
        // 创建一个名为 textLineBreak 的过滤器，用于处理行尾省略号
        textLineBreak: function(text, maxLength, lineBreakMode) {
            // 如果 text 为 undefined、null 或空字符串，则返回空字符串
            if (text === undefined || text === null || text.length === 0) {
                return ''
            }
            // 如果 lineBreakMode 为 undefined 或 null，则设置默认值为 LineBreakMode.EllipsisTruncatingTail
            if (lineBreakMode === null || lineBreakMode === undefined) {
                lineBreakMode = LineBreakMode.EllipsisTruncatingTail
            }
            // 根据 lineBreakMode 的值执行相应的操作
            switch (lineBreakMode) {
                case LineBreakMode.WrappingTruncatingTail:
                    return text.substr(0, maxLength)
                case LineBreakMode.WrappingTruncatingHead:
                    return text.substr(-maxLength)
                case LineBreakMode.EllipsisTruncatingTail:
                    return text.substr(0, maxLength) + (text.length > maxLength ? '...' : '')
                case LineBreakMode.EllipsisTruncatingMiddle:
                    let resultText = text.substr(0, maxLength)
                    // 如果 text 长度大于 maxLength，则在文本中间插入省略号
                    if (text.length > maxLength) {
                        return resultText.substr(0, parseInt(maxLength / 2)) + '...' + resultText.substr(parseInt(maxLength / 2))
                    }
                    return resultText
                case LineBreakMode.EllipsisTruncatingHead:
                    return (text.length > maxLength ? '...' : '') + text.substr(-maxLength)
            }
            // 如果没有匹配到任何 case，则返回原始文本
            return text
        }
    }
}