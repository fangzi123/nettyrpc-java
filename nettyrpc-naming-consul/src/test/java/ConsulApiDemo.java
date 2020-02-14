import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.agent.model.Service;
import com.ecwid.consul.v1.health.model.HealthService;

import java.util.List;
import java.util.Map;
  
public class ConsulApiDemo {  
  
  
    public static void serviceApiGet() {
         ConsulRawClient client = new ConsulRawClient("localhost", 8500);  
         ConsulClient consul = new ConsulClient(client);  
         //获取所有服务  
         Map<String, Service> map = consul.getAgentServices().getValue();
         List<HealthService> list = consul.getHealthServices("lms", false, null).getValue();
         System.out.println(map.size()+"," +map);
         System.out.println("list" + list);
    }

    public static void serviceRegistry() {
        ConsulRawClient client = new ConsulRawClient("localhost", 8500);
        ConsulClient consul = new ConsulClient(client);

        NewService newService = new NewService();
        newService.setAddress("127.0.0.1");
        newService.setPort(8080);
        newService.setName("lms");
        NewService.Check check = new NewService.Check();
        check.setHttp("127.0.0.1:8080");
        check.setInterval("3s");
        newService.setCheck(check);

        consul.agentServiceRegister(newService);
    }
    public static void main(String[] args) {
        serviceRegistry();
        serviceApiGet();  
        System.exit(0);
    }  
}