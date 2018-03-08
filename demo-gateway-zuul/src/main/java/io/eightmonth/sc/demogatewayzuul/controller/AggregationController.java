package io.eightmonth.sc.demogatewayzuul.controller;

import com.google.common.collect.Maps;
import io.eightmonth.sc.demogatewayzuul.entity.User;
import io.eightmonth.sc.demogatewayzuul.service.AggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.Observer;

import java.util.HashMap;

@RestController
public class AggregationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationController.class);

    @Autowired
    private AggregationService  aggregationService;

    // 集合多个服务为一个服务
    @GetMapping("/aggregate/{id}")
    public DeferredResult<HashMap<String, User>> aggregate(@PathVariable Long id){
        Observable<HashMap<String, User>> result = this.aggregateObservable(id);
        return this.toDeferredResult(result);
    }

    public Observable<HashMap<String, User>> aggregateObservable(Long id){
        // 合并两个或者多个Observables发射出的数据项， 根据指定的函数变换它们
        return Observable.zip(
                aggregationService.getUserById(id),
                aggregationService.getMovieUserByUserId(id),
                (user, movieUser) -> {
                    HashMap<String, User> map = Maps.newHashMap();
                    map.put("user", user);
                    map.put("movieUser", movieUser);
                    return map;
                }
        );
    }

    public DeferredResult<HashMap<String, User>> toDeferredResult(Observable<HashMap<String, User>> detalis){
        DeferredResult<HashMap<String, User>> result = new DeferredResult<>();
        // 订阅
        detalis.subscribe(new Observer<HashMap<String, User>>() {
            @Override
            public void onCompleted() {
                LOGGER.info("完成...");
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.info("发生错误...", throwable);
            }

            @Override
            public void onNext(HashMap<String, User> movieDetails) {
                result.setResult(movieDetails);
            }
        });
        return result;
    }
}
