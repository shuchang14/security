layui.define('jquery', function(exports) {
    var $ = layui.jquery;
    var baseService='/';
    var common={
        baseService:function(){
            return baseService;
        },
        formData:function(id){
            var obj = new Object();
            var t = $(id).serializeArray();
            $.each(t, function() {
                obj[this.name] = this.value;
            });
            return obj;
        },
        baseAjax:function (type,url,data,callBack,errorBack) {
            $.ajax({
                //请求方式
                type:type,
                //发送请求的地址
                url:baseService+url,
                //服务器返回的数据类型
                dataType:'json',
                //发送到服务器的数据，对象必须为key/value的格式，jquery会自动转换为字符串格式
                data:data,
                success:function(result){
                    //请求成功函数内容
                    callBack(result);
                },
                error:function(jqXHR){
                    //请求失败函数内容
                    errorBack(jqXHR);
                }
            });
        }
    }
    exports('common', common);
});
