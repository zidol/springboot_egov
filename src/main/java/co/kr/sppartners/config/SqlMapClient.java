package co.kr.sppartners.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.cmmn.trace.handler.DefaultTraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.handler.TraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import org.egovframe.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.egovframe.rte.psl.dataaccess.mapper.MapperConfigurer;
import org.egovframe.rte.psl.orm.ibatis.SqlMapClientFactoryBean;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.AntPathMatcher;

import javax.sql.DataSource;

@Configuration
//@MapperScan(basePackages = "com.example.mapper")
@EnableTransactionManagement
public class SqlMapClient {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
//        dataSource.setUrl("jdbc:mariadb://localhost:3306/test");
        dataSource.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
        dataSource.setUrl("jdbc:log4jdbc:mysql://localhost:3306/test");
        dataSource.setUsername( "root" );
        dataSource.setPassword( "sp160114!" );
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSession(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
        sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));

        return sessionFactory.getObject();
    }

    @Bean
    MapperConfigurer mapperConfigurer(DataSource dataSource) {
        MapperConfigurer mapperConfigurer = new MapperConfigurer();
        mapperConfigurer.setBasePackage("co.kr.sppartners");
        return mapperConfigurer;
    }

    @Bean
    public LeaveaTrace leaveaTrace(DefaultTraceHandleManager traceHandlerService) {
        LeaveaTrace leaveaTrace = new LeaveaTrace();
        leaveaTrace.setTraceHandlerServices(new TraceHandlerService[]{traceHandlerService});
        return leaveaTrace;
    }

    @Bean
    public DefaultTraceHandleManager traceHandlerService(AntPathMatcher antPathMatcher, DefaultTraceHandler defaultTraceHandler) {
        DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
        defaultTraceHandleManager.setReqExpMatcher(antPathMatcher);
        defaultTraceHandleManager.setPatterns(new String[]{"*"});
        defaultTraceHandleManager.setHandlers(new TraceHandler[]{defaultTraceHandler});
        return defaultTraceHandleManager;
    }

    @Bean
    public AntPathMatcher antPathMatcher() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return antPathMatcher;
    }

    @Bean
    public DefaultTraceHandler defaultTraceHandler () {
        DefaultTraceHandler defaultTraceHandler = new DefaultTraceHandler();
        return defaultTraceHandler;
    }
}
