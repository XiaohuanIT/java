lists.newarraylist()和new arraylist() 区别
lists.newarraylist()：

List<String> list = new ArrayList<String>(); 

new arraylist() ：

List<String> list = Lists.newArrayList();

Lists和Maps是两个工具类, Lists.newArrayList()其实和new ArrayList()几乎一模一样, 唯一它帮你做的(其实是javac帮你做的), 就是自动推导(不是"倒")尖括号里的数据类型. 

