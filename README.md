领域驱动设计示例项目
语雀上面也有该文档：https://www.yuque.com/g/zhuagedoupi/kpq2c5/oxsxciclydikng7m/collaborator/join?token=vzfZd1fHrSskNspZ&source=doc_collaborator# 《人人都能DDD》
# DDD是什么
> 领域驱动设计(Domain Driven Design) 是一种从系统分析到软件建模的一套方法论。以领域为核心驱动力的设计体系。
在我学习DDD时，书籍中很大一部分内容都是围绕Model Driven Design（模型驱动设计）在讲解。其实DDD的一部分核心，确实也是Model Driven Design。对我们一线开发来讲，DDD最有价值的地方，就是帮助我们设计出高质量的模型，去解决软件的复杂性。

# 为什么需要DDD
> 1. 对于一个架构师来说，在软件开发中如何降低系统复杂度是一个永恒的挑战。
> 2. 复杂系统设计： 系统多，业务逻辑复杂，概念不清晰，有什么合适的方法帮助我们理清楚边界，逻辑和概念
> 3. 多团队协同： 边界不清晰，系统依赖复杂，语言不统一导致沟通和理解困难。有没有一种方式把业务和技术概念统一，大家用一种语言沟通。例如：航程是大家所理解的航程吗？
> 4. 设计与实现一致性： PRD，详细设计和代码实现天差万别。有什么方法可以把业务需求快速转换为设计，同时还要保持设计与代码的一致性？
> 5. 架构统一，可复用资产和扩展性： 当前取决于开发的同学具备很好的抽象能力和高编程的技能。有什么好的方法指导我们做抽象和实现。

我经常听到很多人会问:\
Q:什么样的项目可以用DDD？\
A:在web开发这个行业里面，90%以上的项目都应该采用DDD。\
Q:MVC在这个行业里当老大哥跑了那么多年，DDD真的就比它牛逼吗？\
A:其实拿MVC和DDD来做比较是不妥的，MVC是一种项目分层方式，DDD是一种设计方法论。我们完全可以基于MVC来使用DDD开发项目。

# 怎么DDD
网上很多的文章，一上来就会给你讲DDD中的战略设计，然后你就会看得云里雾里，最后看完也不知道讲了个啥，代码也不知道怎么写。\
好的学习方式，应当是自顶向下，先知道怎么做，再去探究其背后的含义以及更深层次的原理。所以我们直接从战术开始讲。

## 项目分层
//todo 图片
**适配层（adpater，也有叫用户接口层的）**： 接收一切外部的输入，将这些输入通过适配转化为内部的参数类型，调用应用层的服务。
**应用层（application）**： 尽量简单，不包含业务规则，而只为了下一层中的领域对象做协调任务，分配工作，重点对领域层做编排完成复杂业务场景。应用层表达了项目提供哪些完整功能，比如一个http接口，通常由应用服务的一个方法完成。
**领域层Domain**： 负责表达业务概念和业务逻辑，领域层是系统的核心；而领域模型又是领域层的核心，也是DDD最关注的部分。注意，领域层不依赖任何模块，这样可以防止开发人员将领域模型和技术细节耦合在一起，让人更加专注于模型的设计。
**基础层（infrastructure）**： 对所有上层提供技术能力，技术的细节都在这一层实现。领域模型的仓储实现是脱离不了技术的，所以将实现放在基础层，以便调用各种技术组件。

依赖关系：用 户接口层->应用层->领域层->基础层

可以看到，整个项目里面仍然包含了MVC中的controller、service、dao。我们只是在这个基础上做了增强。
很多人在使用传统的MVC开发项目时，都会有意识得去划分很多的包，封装很多的基础服务类，DDD只是让这些原本就存在的东西，概念化得呈现了出来。因此我们在做设计、写代码的时候，很多时候不必再去纠结这个类该放哪里，这个逻辑由这个类承担又合不合适。

**选择充血模型还是贫血模型？**
强烈建议使用充血模型，充血模型才是最能直观反映领域知识的模型，并且它可以帮助你更好得进行面向对象的程序设计，特别是许多设计模式可以很自然得应用在这上面。

## 分析需求，设计你的领域模型
项目分层是通用的东西，提前搭好就行了。而我们每次来需求的时候，分析完，做技术设计，第一步则应是去设计并抽象出领域模型。\
我在专门学习DDD之前，虽然也会先去设计领域模型，但是往往设计出的模型都无法脱离数据库的设计（与其如此，还不如直接设计数据库来得快）。这是一种思维定式，没有成体系的方法论，就总是会纠结于这个怎么通过技术去实现，最终设计的模型也就脱离不了技术。所以，要怎么去设计模型呢？

### 前提
请保证你的模型命名、字段命名、方法命名，都是需求（业务）中非常明确定义的词汇。领域模型就是表达业务的，你的产品经理、运营都应该能够直接看懂你的领域模型。通常情况下，领域模型和就是领域层中的类。\
在项目中，和除开发外的成员沟通的过程中，请务必只使用业务中的语言。比如下面这一段反面对话：\
● 产品A：我想给这个用户加一个“好友列表”的功能，能做吗？\
● 开发B：可以，我在数据库加个表保存一下好友和用户的关联关系，添加好友的时候，插入一条数据进这个表里面，查询的时候根据用户id在表里查询出这个用户所有好友的用户id，再根据这些用户id反查一下用户表就可以了。\
● 产品A：别说了，你就说能做不能做吧？\
● 开发B：可以\
\
你的需求方并不关注你是怎么实现的，与他们沟通时不要传递技术细节，这样只会增加理解成本，让沟通更费劲。\
在DDD中，这种团队内达成共识的、用于沟通的并能够准确描述业务含义和规则语言叫做**UBIQUITOUS LANGUAGE（通用语言）**。

### 需求来了
比如现在我们要做一个学生管理系统。初版就做个列表页，分页展示所有的学生信息，每个学生需要展示学号、类型、姓名、班级、年龄、住址、联系电话。
支持新增、编辑、删除学生，每个学生可以查看详情，详情中除了列表中展示的信息外，还需要包含备注信息。
列表查询时支持根据学号、班级、联系电话的精确查询，支持姓名的模糊查询。
新增学生时，学号不允许重复，姓名最长为16个字符，班级为下拉菜单选项（需要系统内置好选项），学生类型可选项为：普通学生、小队长、中队长、大队长，年龄需要大于0且小于100，住址最长为100个字符，均不允许为空。描述最长为2000个字符，可以为空。
编辑学生时，学号不可编辑，其余信息均可编辑。

### 初步建模
基于上面的需求，我们可以先想想，如果这个程序仅仅只是运行在内存中，不涉及任何数据库持久化操作，不提供给外部任何的接口，仅仅是我们自己在程序里面模拟一下这些需求，应该怎么做。
//todo 补图
目前为止，看起来需求里面涉及到的模型我们都已经体现在这个设计里面了。但是细想一下，需求里面一些隐含的东西，我们的学生是支持分页查询的，那么在新增出学生后，总该有个地方去存放一下吧，比如弄一个ArrayList，把创建出的学生都存进去，然后我们就能在查询时，通过这个List去筛选出想要的学生了。但是刚才说过了，模型的概念应该属于UBIQUITOUS LANGUAGE，ArrayList是我们代码中的技术概念，不应该出现在模型里面。因此DDD引入了一个通用的概念，叫做repository（仓库），用于表达我们的数据存储的地方，它的行为往往和一个集合很类似。\
请一定把repository当做是领域模型的一部分，而不要将其当做持久化的工具。\
那么我们把这个加入到模型里面去看看：\
\
我这里给出一个简单的设计：\
//todo 补图

### 实体
实体是领域模型中具有唯一标识（id）的对象，那么我们就要去思考一下，哪些模型需要有唯一标识？在业务中对于什么模型，我们需要把两个看起来“相同”的对象给区分开来？\
StudentRepository通常在整个系统里面只存在，没必要设计为实体。\
Student很明显是个实体，比如同一个班上可能出现两个“小明”，我们需要用id去区分它们。虽然在这个需求中，学号是一个唯一的属性，但是我仍然建议所有的实体都用id来表达唯一标识，这样具有通用性。一旦你和别人在交流id的时候，就知道这个东西代表实体的唯一标识。当然，在你实现这个id的时候，可以用学号来实现它，这样就避免了额外的分配id的工作。\
SchoolClass在这个需求中没有区分的必要，仅仅只是作为一个值来展示使用，所以不需要设计为实体。\
StudentDetail也没有区分的必要，因为它只会在我们查看一个学生的详情时才会显示，也不需要设计为实体。\
//todo 补图

### 值对象
值对象相对于实体，我们并不关心两个小明谁是谁，只要它是小明就行了，它代表的就是一个值。\
除去刚才的实体，其余的模型都是值对象。\
DDD中，值对象推荐实现为不可变的，这样在可以放心的将值对象进行共享，来节省内存资源。如果要修改值对象，可以直接构造出一个新的对象，然后更新到对应的实体中去。\

### 聚合根
聚合根也是实体，但是它拥有比实体更多的职责，你可以将其认为成一个“接口人”。对系统的所有操作，都应该通过聚合根来进行，聚合根内部去保证数据的完整性、一致性。\
想一下你在电商平台买东西，突然下单报了个system error，虽然你知道这个是系统出bug了，但还是得先找客服，不可能让你直接找到对应的研发来处理这件事。就算平台确认了这是一个代码bug，也不可能直接给你研发的微信，让你们私下直接处理对吧。而且后续你有任何新的问题，也是让你联系客服，客服再转给研发来处理。这里的客服就是一个聚合根。如果你通过一些方式，绕过了客服直接联系到研发处理，平台就可能丢失掉这个问题的处理记录，这样就无法保证数据的完整性了。\
回到我们的模型，聚合根应该是Student，对学生内部所有属性的操作，都应该通过Student这个模型去进行。\
\
突发情况，来新需求了：\
产品经理说：需要给每个学生绑定一位专属导师，并且这个导师需要在列表页和详情页中展示出来。并且导师管理的功能已经由另外的同事做好了，现在我们只需要将导师列表在绑定导师的页面展示出来，作为选项让用户选择就好了。\
你的teamLeader说：导师管理的功能是另外一个微服务中，你们调一下接口查就行了。\
\
现在需要更新一下模型了，把导师给加进来。导师其实也是一个聚合根，我们的学生只需要引用一下就好了。\
这里会涉及到一个问题，我的一个实体里面，要是关联了一个聚合根，关联的聚合根在这个实体里面是直接表示为id还是表示为一个聚合根对象。\
我的建议是：尽量都以id来表示关联的聚合根。如果需要获取关联的聚合根对象，可以通过对应的repository根据id来查询。而这个查询逻辑可以封装成一个getter方法，返回关联的聚合根对象，这样一来对使用这个实体的人而言，获取关联的聚合根对象就非常简单了。\
原因：\
1.聚合根对象往往会比较大，这样在实体对象创建的时候，就需要加载出这个聚合根对象，会产生内存的浪费，而且增加了响应时间以及构造实体对象的代码复杂度。\
2.关联的聚合根对象，可能还会嵌套关联其他的聚合根，这样级联得加载出来非常消耗资源。\

对于实体，我们给它添加上id:\
//todo 补图
如果一个学生可以绑定多个导师呢？那么Student关联的则应该是一个导师列表。这个导师列表在领域层中就是一个接口，不需要考虑实现，后面在基础设施层中你可以用student表的一个字段来维护导师id列表，也可以单独建一个学生_导师的关联表，根据studentId来查询出来。\
//todo 补图

## 编写领域层的代码
代码比较多，直接看源码吧：
//todo 补图
* 学生抽象出了一个StudentAbility接口，这个接口定义了学生的所有属性的查询以及行为操作。Student则是实现了StudentAbility的学生类。抽象出StudentAbility的原因是：在实际场景中，小队长、中队长、大队长都会有不同的职责与能力，如果后续需求定义了这些职责，我们可以为每一种类型的学生定义一个单独的class并实现StudentAbility接口，这样可以避免Student类的逻辑膨胀，混杂各种类型的职责在里面。
* 聚合根通常需要有一个工厂方法来负责创建对象，简易的实现就是直接在聚合根类上面定义一个静态的create方法，create方法的参数定义需要尽量简单，内部逻辑需要包含完整的参数校验。对于复杂的创建逻辑，可以单独定义一个工厂类来负责对象创建。
* repository只定义成了接口，在现在这个内存模拟的程序中，它可以通过ConcurrentHashMap来实现，在真实场景中，它可以通过你的orm框架来实现（如mybatis）。通常聚合根都需要有一个repository来进行存储。
* IdGenerator是用于生成实体id的接口，建议在创建实体对象时，都采用预先生成id的方式，可以避免很多麻烦。
* DDD中对领域模型的方法定义分为两类：query和command，即查询和命令。query方法就是读的逻辑，command就是写的逻辑。尽量保证读写分离，不要要求一个方法既可以读又可以写，这会产生副作用，导致职责不清晰以及测试的一些困难。

```java
package com.hyz.ddd.demo.domain.model;

import cn.hutool.extra.spring.SpringUtil;
import com.hyz.ddd.demo.domain.cmd.CreateStudentCmd;
import com.hyz.ddd.demo.domain.cmd.EditStudentCmd;
import com.hyz.ddd.demo.domain.exception.ParamException;
import com.hyz.ddd.demo.domain.repository.MentorRepo;
import com.hyz.ddd.demo.domain.repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 学生
 */
public class Student implements StudentAbility {

    private final Long id;

    private String name;

    private Type type;

    /**
     * 学号
     */
    private final String studyNumber;

    private SchoolClass schoolClass;

    private Integer age;

    private String address;

    private String phone;
    private final StudentDetailRepo studentDetailRepo;
    private final MentorRepo mentorRepo;
    private final BoundMentorList boundMentorList;

    public Student(Long id, Type type, String name, String studyNumber, SchoolClass schoolClass, Integer age, String address, String phone) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.studyNumber = studyNumber;
        this.schoolClass = schoolClass;
        this.age = age;
        this.address = address;
        this.phone = phone;
        this.studentDetailRepo = SpringUtil.getBean(StudentDetailRepo.class);
        this.mentorRepo = SpringUtil.getBean(MentorRepo.class);
        BoundMentorListFactory boundMentorListFactory = SpringUtil.getBean(BoundMentorListFactory.class);
        this.boundMentorList = boundMentorListFactory.create(id);
    }

    /**
     * 创建学生
     * @param createStudentCmd
     * @return
     */
    public static Student create(CreateStudentCmd createStudentCmd) {
        Type type = Optional.ofNullable(createStudentCmd.getType())
                .orElseThrow(() -> new ParamException("type cannot be null"));
        String studyNumber = checkStudyNumber(createStudentCmd.getStudyNumber());
        String name = checkName(createStudentCmd.getName());
        SchoolClass schoolClass = Optional.ofNullable(createStudentCmd.getSchoolClass())
                .orElseThrow(() -> new ParamException("school class cannot be null"));
        Integer age = checkAge(createStudentCmd.getAge());
        String address = checkAddress(createStudentCmd.getAddress());
        String phone = Optional.ofNullable(createStudentCmd.getPhone())
                .orElseThrow(() -> new ParamException("phone cannot be null"));
        String remark = checkRemark(createStudentCmd.getRemark());

        IdGenerator idGenerator = SpringUtil.getBean("studentIdGenerator", IdGenerator.class);
        Long studentId = idGenerator.nextId();
        StudentDetail studentDetail = new StudentDetail(remark);
        SpringUtil.getBean(StudentDetailRepo.class).add(studentId, studentDetail);
        return new Student(studentId, type, name, studyNumber, schoolClass, age, address, phone);
    }

    /**
     * 校验学号
     * @param studyNumber
     * @return
     */
    private static String checkStudyNumber(String studyNumber) {
        if (studyNumber == null) {
            throw new ParamException("studyNumber cannot be null");
        }
        StudentRepo studentRepo = SpringUtil.getBean(StudentRepo.class);
        //学号不能重复
        studentRepo.getByStudyNumber(studyNumber)
                .orElseThrow(() -> new ParamException("studyNumber already existed"));
        return studyNumber;
    }

    /**
     * 校验备注
     * @param remark
     * @return
     */
    private static String checkRemark(String remark) {
        if (remark == null) {
            return null;
        }
        if (remark.length() > 2000) {
            throw new ParamException("length of remark cannot be more than 2000");
        }
        return remark;
    }

    /**
     * 校验地址
     * @param address
     * @return
     */
    private static String checkAddress(String address) {
        if (address == null) {
            throw new ParamException("address cannot be null");
        }
        if (address.length() > 100) {
            throw new ParamException("length of address cannot be more than 100");
        }
        return address;
    }

    /**
     * 校验名字
     * @param name
     * @return
     */
    private static String checkName(String name) {
        if (name == null) {
            throw new ParamException("name cannot be null");
        }
        if (name.length() > 16) {
            throw new ParamException("length of name cannot be more than 16");
        }
        return name;
    }

    /**
     * 校验年龄
     * @param age
     * @return
     */
    private static Integer checkAge(Integer age) {
        if (age == null) {
            throw new ParamException("age cannot be null");
        }
        if (age <= 0 || age > 100) {
            throw new ParamException("age should between 0 and 100");
        }
        return age;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getStudyNumber() {
        return studyNumber;
    }

    @Override
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    @Override
    public Integer getAge() {
        return age;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    /**
     * 查看详情
     * @return
     */
    @Override
    public StudentDetail getDetail() {
        return studentDetailRepo.getByStudentId(this.id)
                .orElseThrow(() -> new RuntimeException("cannot find detail"));
    }

    /**
     * 编辑学生
     * @param editStudentCmd
     */
    @Override
    public void editStudent(EditStudentCmd editStudentCmd) {
        if (editStudentCmd.getPhone() == null) {
            throw new ParamException("phone cannot be null");
        }
        if (editStudentCmd.getSchoolClass() == null) {
            throw new ParamException("school class cannot be null");
        }
        if (editStudentCmd.getType() == null) {
            throw new ParamException("type cannot be null");
        }
        String name = checkName(editStudentCmd.getName());
        String address = checkAddress(editStudentCmd.getAddress());
        Integer age = checkAge(editStudentCmd.getAge());

        String remark = checkRemark(editStudentCmd.getRemark());
        StudentDetail detail = getDetail();
        studentDetailRepo.remove(this.id, detail);
        studentDetailRepo.add(this.id, new StudentDetail(remark));

        this.name = name;
        this.address = address;
        this.age = age;
        this.phone = editStudentCmd.getPhone();
        this.schoolClass = editStudentCmd.getSchoolClass();
        this.type = editStudentCmd.getType();
    }

    /**
     * 绑定导师
     * @param mentorIds
     */
    @Override
    public void bindMentors(List<Long> mentorIds) {
        List<Mentor> mentors = mentorRepo.listByIds(mentorIds);
        mentors.forEach(boundMentorList::add);
    }

    /**
     * 查询已绑定的导师
     * @return
     */
    @Override
    public List<Mentor> queryBoundMentors() {
        return boundMentorList.listAll();
    }
}
```

## 编写基础设施层的代码
//todo 补图
* 基础设施层就是应用我们的各种技术去为领域层服务。领域层定义的接口在这里进行实现，factory、model、repository包中都是这些实现。
* 导师(Mentor)的rpc调用在RpcMentorRepoImpl中进行，因为rpc本身是一个技术概念，它不应该出现在领域层中。
* 从数据库中还原出存储的领域对象，这个过程其实也是一个创建领域对象的过程，但是这个过程不应该通过我们之前定义的工厂方法来进行，因为已存储的领域对象数据是一个事实，不需要再经过一系列的参数校验，并且我们应该容忍某些异常的数据，保证对象的最小可用。我通常会在repository的实现中定义一个restore方法完成这个还原的过程，其中会直接调用领域模型的构造函数进行对象创建。

```java

/**
 * name：mybatis plus 实现的学生仓库
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/22
 */
@Repository
public class MpStudentRepoImpl implements StudentRepo {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public void add(@NonNull StudentAbility student) {
        StudentDO studentDO = convertToDO(student);
        LocalDateTime now = LocalDateTime.now();
        studentDO.setCreatedAt(now);
        studentDO.setUpdatedAt(now);
        studentMapper.insert(studentDO);
    }

    @Override
    public Page<StudentAbility> queryPage(@NonNull StudentPageQuery studentPageQuery) {
        LambdaQueryWrapper<StudentDO> queryWrapper = new LambdaQueryWrapper<StudentDO>()
                .eq(studentPageQuery.getStudyNumber() != null, StudentDO::getStudyNumber, studentPageQuery.getStudyNumber())
                .like(studentPageQuery.getNameKey() != null, StudentDO::getName, studentPageQuery.getNameKey())
                .eq(studentPageQuery.getSchoolClass() != null, StudentDO::getSchoolClass, studentPageQuery.getSchoolClass().getName())
                .eq(studentPageQuery.getPhone() != null, StudentDO::getPhone, studentPageQuery.getPhone())
                .eq(StudentDO::getDeleted, false);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<StudentDO> studentDOPage =
                studentMapper.selectPage(com.baomidou.mybatisplus.extension.plugins.pagination.Page.of(studentPageQuery.getPageNo(), studentPageQuery.getPageSize()), queryWrapper);
        List<StudentAbility> studentList = studentDOPage.getRecords()
                .stream()
                .map(this::restore)
                .collect(Collectors.toList());
        return new Page<>(studentList, studentPageQuery.getPageNo(), studentPageQuery.getPageSize(), studentDOPage.getTotal());
    }

    @Override
    public Optional<StudentAbility> getById(@NonNull Long studentId) {
        return Optional.ofNullable(studentMapper.selectOne(new LambdaQueryWrapper<StudentDO>()
                    .eq(StudentDO::getId, studentId)
                    .eq(StudentDO::getDeleted, false)))
                .map(this::restore);
    }

    @Override
    public void save(@NonNull StudentAbility student) {
        getById(student.getId())
                .ifPresent(studentAbility -> studentMapper.updateById(convertToDO(student)));
    }

    @Override
    public Optional<StudentAbility> getByStudyNumber(String studyNumber) {
        return Optional.ofNullable(studentMapper.selectOne(new LambdaQueryWrapper<StudentDO>()
                        .eq(StudentDO::getStudyNumber, studyNumber)
                        .eq(StudentDO::getDeleted, false)))
                .map(this::restore);
    }

    /**
     * 转化成数据库存储的数据对象
     * @param student
     * @return
     */
    private StudentDO convertToDO(StudentAbility student) {
        if (student == null) {
            return null;
        }
        StudentDO studentDO = new StudentDO();
        BeanUtils.copyProperties(student, studentDO);
        studentDO.setDeleted(false);
        return studentDO;
    }

    /**
     * 从数据库还原领域对象
     * @param studentDO
     * @return
     */
    private StudentAbility restore(StudentDO studentDO) {
        if (studentDO == null) {
            return null;
        }
        return new Student(studentDO.getId(), studentDO.getType(), studentDO.getName(), studentDO.getStudyNumber(),
                studentDO.getSchoolClass(), studentDO.getAge(), studentDO.getAddress(), studentDO.getPhone());
    }
}
```

## 编写应用层的代码
//todo 补图
* appication层是非常薄的一层，因为业务逻辑都内聚到了聚合根中，这里只需要从repository中获取聚合根，调用聚合根的方法，如果有对聚合根进行修改，再保存到repository中就好了。
* appication层也可以对多个领域进行编排。

```java
package com.hyz.ddd.demo.application.service;

import com.hyz.ddd.demo.application.dto.*;
import com.hyz.ddd.demo.domain.cmd.CreateStudentCmd;
import com.hyz.ddd.demo.domain.cmd.EditStudentCmd;
import com.hyz.ddd.demo.domain.exception.ParamException;
import com.hyz.ddd.demo.domain.model.Page;
import com.hyz.ddd.demo.domain.model.Student;
import com.hyz.ddd.demo.domain.model.StudentAbility;
import com.hyz.ddd.demo.domain.query.StudentPageQuery;
import com.hyz.ddd.demo.domain.repository.StudentRepo;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生app服务
 * 提供学生相关的应用能力
 */
@Service
public class StudentAppService {

    @Autowired
    @Qualifier("mpStudentRepoImpl")
    private StudentRepo studentRepo;

    /**
     * 新建学生
     * @param createStudentCmd
     */
    public void addNewStudent(CreateStudentCmd createStudentCmd) {
        Student student = Student.create(createStudentCmd);
        studentRepo.add(student);
    }

    /**
     * 查询学生
     * @param studentPageQuery
     * @return
     */
    public Page<StudentDTO> queryStudentPage(StudentPageQuery studentPageQuery) {
        Page<StudentAbility> page = studentRepo.queryPage(studentPageQuery);
        List<StudentDTO> studentDTOS = page.getDataList().stream()
                .map(StudentDTO::from)
                .collect(Collectors.toList());
        return new Page<>(studentDTOS, page.getPageNo(), page.getPageSize(), page.getTotalCount());
    }

    /**
     * 查询学生详情
     * @param studentId
     * @return
     */
    public StudentDetailDTO queryStudentDetail(Long studentId) {
        return studentRepo.getById(studentId)
                .map(StudentDetailDTO::from)
                .orElseThrow(() -> new ParamException("student not found"));
    }

    /**
     * 编辑学生
     * @param editStudentParam
     */
    public void editStudent(EditStudentParam editStudentParam) {
        StudentAbility student = studentRepo.getById(editStudentParam.getStudentId())
                .orElseThrow(() -> new ParamException("student not found"));
        EditStudentCmd editStudentCmd = new EditStudentCmd();
        BeanUtils.copyProperties(editStudentCmd, editStudentCmd);
        student.editStudent(editStudentCmd);
        studentRepo.save(student);
    }

    /**
     * 绑定导师
     * @param bindMentorParam
     */
    public void bindMentors(BindMentorParam bindMentorParam) {
        StudentAbility student = studentRepo.getById(bindMentorParam.getStudentId())
                .orElseThrow(() -> new ParamException("student not found"));
        student.bindMentors(bindMentorParam.getMentorIds());
    }

    /**
     * 查询已绑定的导师
     * @param studentId
     * @return
     */
    public List<MentorDTO> queryBoundMentors(@NonNull Long studentId) {
        StudentAbility student = studentRepo.getById(studentId)
                .orElseThrow(() -> new ParamException("student not found"));
        return student.queryBoundMentors()
                .stream()
                .map(MentorDTO::from)
                .collect(Collectors.toList());
    }
}
```

## 编写适配层代码
//todo 补图
demo里面的适配层中就只有controller，提供对外的http接口。controller里面只是将前端的参数转化为application层的参数，然后调用对应的service方法。如果直接用application层的参数来接收前端参数的话，则可以省略这个转化过程，但是往往对外暴露的接口参数不是完全自己可控的，所以需要这么一层适配转化。
```java
package com.hyz.ddd.demo.adapter.controller;

import com.hyz.ddd.demo.adapter.common.Result;
import com.hyz.ddd.demo.application.dto.*;
import com.hyz.ddd.demo.application.service.StudentAppService;
import com.hyz.ddd.demo.domain.cmd.CreateStudentCmd;
import com.hyz.ddd.demo.domain.model.Page;
import com.hyz.ddd.demo.domain.query.StudentPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * name：学生api
 * desc:
 *
 * @author HuangYaZhe
 * @since 2023/5/18
 */
@RestController
public class StudentController {

    @Autowired
    private StudentAppService studentAppService;

    /**
     * 新建学生
     * @param createStudentCmd
     * @return
     */
    @PostMapping("student")
    public Result<Void> addNewStudent(CreateStudentCmd createStudentCmd) {
        studentAppService.addNewStudent(createStudentCmd);
        return Result.ok();
    }

    /**
     * 查询学生列表
     * @param studentPageQuery
     * @return
     */
    @GetMapping("studentPage")
    public Result<Page<StudentDTO>> queryStudentPage(@RequestBody StudentPageQuery studentPageQuery) {
        return Result.ok(studentAppService.queryStudentPage(studentPageQuery));
    }

    /**
     * 查询学生详情
     * @param studentId
     * @return
     */
    @GetMapping("studentDetail")
    public Result<StudentDetailDTO> queryStudentDetail(@RequestParam Long studentId) {
        return Result.ok(studentAppService.queryStudentDetail(studentId));
    }

    /**
     * 编辑学生
     * @param editStudentParam
     * @return
     */
    @PostMapping("editStudent")
    public Result<Void> editStudent(@RequestBody EditStudentParam editStudentParam) {
        studentAppService.editStudent(editStudentParam);
        return Result.ok();
    }

    /**
     * 绑定导师
     * @param bindMentorParam
     * @return
     */
    @PostMapping("student/bindMentors")
    public Result<Void> bindMentors(@RequestBody BindMentorParam bindMentorParam) {
        studentAppService.bindMentors(bindMentorParam);
        return Result.ok();
    }

    /**
     * 查询已绑定的导师
     * @param studentId 学生id
     * @return
     */
    @GetMapping("student/queryBoundMentors")
    public Result<List<MentorDTO>> queryBoundMentors(@RequestParam Long studentId) {
        return Result.ok(studentAppService.queryBoundMentors(studentId));
    }
}
```

# 总结
ddd的核心在于领域模型的抽象，也就是建模。本例的建模方法是非常初级的，仅仅只是通过找名词的方式完成了模型抽象，然而高级的建模设计一定是基于丰富的领域知识，才能够设计出真正适合业务的模型。\
\
参考资料：\
1.https://blog.csdn.net/alibabatech1024/article/details/125674376 \
2.《领域驱动设计-软件核心复杂性应对之道》
