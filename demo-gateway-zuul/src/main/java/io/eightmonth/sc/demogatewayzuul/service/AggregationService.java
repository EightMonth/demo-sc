package io.eightmonth.sc.demogatewayzuul.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.eightmonth.sc.demogatewayzuul.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

@Service
public class AggregationService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getUserById(Long id){
        // 创建一个衩观察者
        return Observable.create(observer -> {
           User user = restTemplate.getForObject("http://provider-user/{id}", User.class, id);
           observer.onNext(user);
           observer.onCompleted();
        });
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public Observable<User> getMovieUserByUserId(Long id){
        return Observable.create(observer -> {
           User movieUser = restTemplate.getForObject("http://consumer-movice/user/{id}", User.class, id);
           observer.onNext(movieUser);
           observer.onCompleted();
        });
    }

    public User fallback(Long id){
        User user = new User();
        user.setId(-3L);
        user.setName("默认用户");
        return user;
    }
}
