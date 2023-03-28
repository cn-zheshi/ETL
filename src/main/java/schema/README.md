### Assignment2 说明文档

本次作业由五个.xsd文件组成，分别对应department，PersonInfo，Student，Course，StudentList五张schema

对于Department表，该Schema包含一个部门元素，该元素属于一个自定义类型”部门类型“，该类型的可能取值是固定的，因此可通过枚举（enumeration）来定义可接受值的列表。

对于PersonInfo表，除了基本的姓名，性别等信息外，还需包含部门信息，因此需要引用Department.xsd，PersonInfo中的部门属性属于Department.xsd中定义的”部门类型“。此外，对于性别和身份证号这两个属性，应当具有一些限制，应当分别定义两个类型。性别类型通过枚举定义全部可接受值的列表；身份证号则通过pattern限定可接受的字符串是符合规范的身份证号格式。

对于Student表，除了学号，年级，班级等学籍相关信息，还应包括基本个人信息，因此需要引用PersonInfo.xsd，被定义在”个人信息“这一类型之中。该表的学号，年级，班级在格式上具有一些限制，因此应当分别定义三个类型并定义约束。学号应限制长度为9，年级和班级类型由于取值是固定的，因此可以通过枚举来限制。

对于Course表，包含课程编号，课程名称等课程基本信息。其中课程编号类型是一个8位或9位的字符串，因此创建一个新的类型，并用pattern对其形式做限制；校区和课程类型的取值是固定的，因此应当使用枚举来限制。

对于StudentList表，该表是由多个学生元素组织成的列表，因此采用一个sequence元素，其中每个元素来自是一个student，因此需要引用student.xmd，每个元素是该schema中定义的”学生信息“类型。

经检验，Student.xsd和Course.xsd中的类型定义和ScoreList.xsd兼容，满足题目所给要求。