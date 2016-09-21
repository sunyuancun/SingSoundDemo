package com.singsound.singsounddemo.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wang on 2016/9/21.
 */
public class ArticleConfig {

    public static JSONArray getJsonArrayLm(int position) {

        JSONArray jsonArray = new JSONArray();

        switch (position) {
            //basketball
            case 0:
                try {
                    for (int i = 0; i < ans_basketball.length; i++) {
                        JSONObject json = new JSONObject();
                        json.put("answer", 1.0);
                        json.put("text", ans_basketball[i]);
                        jsonArray.put(json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            // friend
            case 1:
                try {
                    for (int i = 0; i < ans_friend_and_i.length; i++) {
                        JSONObject json = new JSONObject();
                        json.put("answer", 1.0);
                        json.put("text", ans_friend_and_i[i]);
                        jsonArray.put(json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }


        return jsonArray;
    }


    public static String[] titles =
            {"关于篮球\n" + "要点：\n" +
                    "1. 运动对人的健康非常有益。\n" +
                    "2. 我最喜欢的运动是篮球，我经常在周末和朋友们在公园打篮球。\n" +
                    "3. 我觉得打篮球是释放压力的最好方式，还可以带给我们许多乐趣。\n" +
                    "4. 姚明是我最喜爱的篮球明星。\n" +
                    "5. 我打算长大后成为一名篮球运动员，所以我现在要每天练习。",

                    "说出下面的话：我和我最好的朋友相处的非常好，但是我们却各有不同。他比我更有趣，更外向，而我很严肃。他是运动型的，各种体育运动都很喜欢，而我更擅长于学习。"
            };

    public static String[] biaozhun_ans = {
            "sports is good for our health. my favourite sport is basketball. i always play basketball with my friends in park. i think playing basketball is the best way to release pressure, it also can bring us a lot of happiness. yaoming is my favourite basketball star. when i grow up, i want to be a basketball player, so i should play basketball everyday.",
            "i get along well with my best friend, but we are different. he is funnier and more opening than me, and i am serious. he is a sportsman and he likes many sports, but i am good at studying."

    };


    public static String[] ans_basketball = {

            "sports is good for our health. my favourite sport is basketball. i always play basketball with my friends in park. i think playing basketball is the best way to release pressure, it also can bring us a lot of happiness. yaoming is my favourite basketball star. when i grow up, i want to be a basketball player, so i should play basketball everyday.",

            "sports is benefit to our health. basketball is my favourite sport. i always play basketball with my friends in park. i think playing basketball is the best way to release pressure, and play basketball can bring us a lot of happiness. yaoming is my favourite basketball star. i want to become a basketball athletes when i grow up, so i have to practice it everyday.",

            "Sports is good for our heath. I like playing basketball very much, and I usually play basketball with my friends in the park on weekdays . I think that playing basketball is the best way to release stress, and give lots of fun to us. Yaoming is my favourite basketball star. When I grow up, I want to be a basketball player, so from now on, I have to play basketball every day.",

            "Doing sports is good for human being's health. My favourite sport is playing basketball, I often play basketball with my friends on weekdays. In my opinion playing basketball is the best way to release our stress, and it also give us some happiness. Yaoming is a famous basketball star, I like him best. I want to be a basketball sportsman, so now I have to play basketball every day.",

            "Sports is good to our health. My favourite sport is basketball, I often play basketball with my friends in the park on weekends. I think play basketball is the best way to release pressure, and can bring us happiness. Yao Ming is my favourite basketball star. I want to be a basketball player when I grow up, so I should exercise everyday from now on.",

            "Sports is benefit to our health. Basketball is my favourite sport, I always play basketball on weekends in the park with my friends. I think play basketball is the best way to relax, and can bring us a lot of fun. Yao Ming is my favourite basketball star. I am going to practise playing basketball everyday so that I can become a basketball player when I grow up.",

            "Doing sports is very helpful to our body.  My favourite sports is playing basketball. I often play basketball with my friends in the park at the weekend. I think playing basketball is the best way to less pressure. It can also bring us lots of fun. Yao ming is my favorite basketball star.  I want to become a basketball player when i grow up, so i need to practice everyday.",

            "doing Sport is useful to our body. My favourite game is basketball. I usually go to play basketball with my friends in the park at the weekend. I think playing basketball is the best way to less pressure. It can also give us lots of fun. I like Yao ming best. I want to become a basketball player when i grow up. So i should practice it everyday.",

            "Sports are beneficial to health. My favourite sport is basketball and I usually play it with my friends in parks on weekends. I think playing basketball is the best way of releasing stress and can bring us a lot of fun. Yao Ming is my favourite basketball star. I plan to become a basketball player when I grow up, so I need to practice everyday. ",

            "Doing exercise can benefit people’s health. Basketball is my favourite sport. On weekends, I usually play basketball with my friends in parks. In my opinion, playing basketball is the best method of releasing pressure and can provide us with a large amount of pleasure. My favourite basketball star is Yao Ming. I dream about being a hoopster when I grow up. As a result of this, I am supposed to practice basketball everyday.",

            "Taking exercise is good for our health. The basketball is the best sport which I like. I will go to the park for playing basketball with my friends on Sunday. I think the basketball serves the best way to release the pressure and find some pleasure at the same time. By playing basketball, we can relax our body and mind. Yaoming is one of my favourite basketball star, I also want to be a professional basketball player when i grow up! so i need to practise every day.",

            "It is very good for our health to do some sports. Basketball is my favourite sport. At weekends, I often play basketball with my friends in the park. I think it is the best way to release my pressure. Meanwhile, I can get more pleasure from playing basketball. My favourite basketball star is Yaoming. I am going to practise playing basketball everyday so that I can become a basketball player when I grow up.",

            "Doing Sports can make us become healthy. My favourite sport is basketball. On weekends, I always round up a few friends to the park to play basketball with me. In my opinion, playing basketball is the best way of unwinding. It can also bring joy for us. Yaoming is my favourite basketball star. I have to do some exercise everyday, because I dream of becoming a basketball player in the future."
    };

    public static String[] ans_friend_and_i = {

            "i get along well with my best friend, but we are different. he is funnier and more opening than me, and i am serious. he is a sportsman and he likes many sports, but i am good at studying.",

            "i always get along very well with my best friend, but we are have the totally different temperament. he is a more interesting and more exoteric person, but i am more  serious. And he is good at any sports, however I am good at studying.",

            "i get along well with my best friend, but we are different. he is funnier and more outgoing than me, and i am more serious than him. he is a sportsman and he likes all kinds of sports, but i am good at studying.",

            "My best friend and I get on with each other very well, but we are different. He is more interesting and outgoing, but I am serious. And he is good at any sports, however I am good at studying.",

            "My best friend and I get along with each other, but we are different. He is funnier and more outgoing than me, and I am more serious. And he likes doing sports, and good at any sports, but I am good at studying.",

            "I get along well with my best friend, but there are a lot of differences among us. Compared with me, he is much funnier and more outgoing, while I am solemn. He is sporty and like every kinds of sports, while I am more good at study.",

            "I get along very nice with my best friend, but we have different.  He is funnier and more outgoing than me, I am serious. He is athletic and he likes all kinds of sports, but I am good at study.",

            "I get along with my best friend very well, but we are different. He is funnier and more outgoing than me, I am serious. He is athletic and likes all kinds of sports, but I'm more good at study.",

            "My friend an I get along with each other very well, but we are different. He is more interesting, more opening, but I am more serious. He is an athletic guy, and like many kinds of sports. But i am good at study.",

            "My best friend an I get along with each other quite well, but we are so different. He is much funnier, more outgoing, but i am more serious. He is an athletic, he likes all kinds of sports, but i am good at study.",

            "I get alone well with my friend, but we have different character. He is funnier and more outgoing than me. However, I am more serious than him. He is an athletic man who likes all kinds of sports very much. Instead, I am good at study.",

            "I and my best friend get along well with each other, although we have a lot of differences. He is more interesting and outgoing than me, while I am a solemn guy. While he is a sporty guy and really like doing variety of sports, I am more excel at academic learning."
    };

}
