
//定义数据模型
Ext.define('js_test.user', {
    extend: 'Ext.data.Model',

    fields: [{
        name: 'id',
        type: 'int'
    }],

    schema: {
        namespace: 'js-test',  // generate auto entityName
        proxy: {     // Ext.util.ObjectTemplate
            type: 'ajax',
            url: '{entityName}.json',
            reader: {
                type: 'json',
                rootProperty: '{entityName:lowercase}'
            }
        }
    }
});

//使用store
var store = new Ext.data.Store ({
    model: 'js_test.user'
});
store.load({
    callback:function(){
        var first_name = this.first().get('name');
        console.log(first_name);
    }
});

//内联数据

new Ext.data.Store({
    model: 'js_test.user',
    data: [{
        id: 1,
        name: "Philip J. Fry"
    },{
        id: 2,
        name: "Hubert Farnsworth"
    },{
        id: 3,
        name: "Turanga Leela"
    },{
        id: 4,
        name: "Amy Wong"
    }]
});