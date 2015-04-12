package com.expercise.service.challenge;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baybatu on 3/30/15.
 */
public class UserTestCaseStateServiceTest {

    class Info {
        String info;

        @Override
        public String toString() {
            return "Info{" +
                    "info='" + info + '\'' +
                    '}';
        }
    }

    @Test
    public void should() {
        Map<String, List<Info>> map = new HashMap<>();

        List<Info> infos1 = new ArrayList<>();
        Info i1 = new Info();
        i1.info = "batuhan";
        infos1.add(i1);

        Info i2 = new Info();
        i2.info = "ahmet";
        infos1.add(i2);

        Info i3 = new Info();
        i3.info = "mehmet";
        infos1.add(i3);

        System.out.println(infos1);

        map.put("1", infos1);

        List<Info> infos2 = new ArrayList<>(infos1);

        Info news = new Info();
        news.info="zaa";
        infos2.add(news);

        i3.info = "denemee";

        System.out.println(infos1);
        System.out.println(infos2);
        System.out.println(map);



    }

}