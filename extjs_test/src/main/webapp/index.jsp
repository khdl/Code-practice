<html>
<head>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/extjs/6.0.0/classic/theme-aria/resources/theme-aria-all.css" rel="stylesheet" />
    <script src="https://cdn.bootcss.com/extjs/6.0.0/ext-all.js"></script>
    <script type="text/javascript">
        Ext.require([
            'Ext.grid.*',
            'Ext.data.*'
        ]);
        // Creation of data model
        Ext.define('StudentDataModel', {
            extend: 'Ext.data.Model',
            fields: [
                {name: 'name', mapping : 'name'},
                {name: 'age', mapping : 'age'},
                {name: 'marks', mapping : 'marks'}
            ]
        });

        Ext.onReady(function(){
            // Store data
            var myData = [
                { name : "Asha", age : "16", marks : "90" },
                { name : "Vinit", age : "18", marks : "95" },
                { name : "Anand", age : "20", marks : "68" },
                { name : "Niharika", age : "21", marks : "86" },
                { name : "Manali", age : "22", marks : "57" }
            ];
            // Creation of first grid store
            var firstGridStore = Ext.create('Ext.data.Store', {
                model: 'StudentDataModel',
                data: myData
            });
            // Creation of first grid
            var firstGrid = Ext.create('Ext.grid.Panel', {
                store            : firstGridStore,
                columns          :
                    [{
                        header: "Student Name",
                        dataIndex: 'name',
                        id : 'name',
                        flex:  1,
                        sortable: true
                    },{
                        header: "Age",
                        dataIndex: 'age',
                        flex: .5,
                        sortable: true
                    },{
                        header: "Marks",
                        dataIndex: 'marks',
                        flex: .5,
                        sortable: true
                    }],
                stripeRows       : true,
                title            : 'First Grid',
                margins          : '0 2 0 0'
            });

            // Creation of a panel to show both the grids.
            var displayPanel = Ext.create('Ext.Panel', {
                width        : 600,
                height       : 200,
                layout       : {
                    type: 'hbox',
                    align: 'stretch',
                    padding: 5
                },
                renderTo     : 'panel',
                defaults     : { flex : 1 },
                items        : [
                    firstGrid
                ]
            });
        });
    </script>
</head>
<body>
<div id = "panel" > </div>
</body>
</html>
