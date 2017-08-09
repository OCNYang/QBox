package com.ocnyang.qbox.app.model.entities.constellation;

import java.util.List;

/*******************************************************************
 *    * * * *   * * * *   *     *       Created by OCN.Yang
 *    *     *   *         * *   *       Time:2017/3/20 11:25.
 *    *     *   *         *   * *       Email address:ocnyang@gmail.com
 *    * * * *   * * * *   *     *.Yang  Web site:www.ocnyang.com
 *******************************************************************/


public class YearConstellation extends RootConstellation {

    /**
     * year : 2017
     * mima : {"info":"放平心态 选择未来","text":["对大多数水瓶座而言，未来一年是不缺风光，却需要自己放平心态，为未来做出选择的一年。命主星土星还将在你们的朋友宫和愿望宫停留几乎整年，你将继续检验和审视自己的朋友圈，抛离\u201c不良资产\u201d，只选择那些与你可以真正\u201c同甘苦共富贵\u201d的朋友共同前进。木天拱持续作用你的人际宫位，也将改变你过去两年一贯深居简出的状态，令当下被无形\u201c禁锢\u201d的状态终于破局，有选择地活跃于各个社交舞台之上，并借助他人之力实现个人价值和水平的提升。对水瓶座来说，难点将不在外界环境好坏（因为已经在2016年形成良性循环），而在于自己可否以坦然心态接受他人的好意，以及明白不切实际的想法必须抛弃才可能更好地出发。木星持续发力也会让你更加明确自己应该选择怎样的方向，抽丝剥茧只留下那些真正适合自己的选择和内涵，进入真正合适自身的领域，尤其利好出版、旅行、考试、出国、高等教育等等行业。"]}
     * career : ["对很多水瓶座而言，明年的人生重点似乎并不在现有事业上，更多在研究和选择未来的发展路线。明年整年，事业宫主火星都在与他人互动的宫位内前进，这让水瓶们很难独立作出决定，任何事业上的选择都必须涉及他人或是工作环境，很多人也会对当下的工作和事业更加厌倦，或是因为家庭原因而无法全心投入工作。对大多数水瓶来说，更好的选择也许还是学习姜太公稳守钓鱼台，不需要过于主动出击，而是等待他人向你伸出橄榄枝，多参与各种不同的团体或是合作活动，配合他人的节奏前行，并在不停的试错和实践中找到自己真正有兴趣的内容和方向，为年底的事业转折埋下成熟的伏笔。"]
     * love : ["2017年土星都还在制约着水瓶座的感情宫，这也让很多水瓶座始终对感情持冷眼旁观的态度，因为想不明白自己到底需要什么样的对象，或是因为对情感关系中可能存在问题的恐惧而不愿意投入感情。另一些水瓶座则可能会倾向于选择年长对象以获得更好的物质支持安抚自己焦虑的内心。但良好的人际关系也会让水瓶座终于在这一年走出自我限制的小圈子，进入更大的社交圈，认识更多不同的大人物，并因为得到欣赏而获得更多人的求欢。部分水瓶座可能会遇到对方因为倾慕自己的才华而越过恋爱步骤直接求婚，或是与重要人物构建一种合作式的伴侣关系，尤其容易在8月份的两次日月食前后确定关系。无论如何，2017年水瓶座都将经历一些与众不同的感情关系，并在他人看来非主流的关系中找到更加合适自己的定位，并有望借力实现社会地位的提升。"]
     * health : ["健康上，可能会出现急性病，例如有炎症、发烧，平时必须多注意饮食习惯。"]
     * finance : ["未来一年，水瓶座的财运虽然不算太好，但却也明显强于2016年，想花钱时也不会缺钱。你可能会热衷于旅行、出国、进修等等增长见识帮助你厘清思路的事情，并在这些项目上花上不少，但也会有机会通过这些方面获得不错的收益。打工族可以尝试利用个人的技术优势和人脉特长尝试私活或是通过推广获得奖励。企业管理者则有机会被人带着尝试一些新的领域，虽然未必大幅盈利，却也基本可以保持不错收益，并让你有望为下一步的大单奠定基础。自雇业者则是机遇最强的一族，亲朋好友介绍来的工作根本做不完，反倒是自己可能对原有工作厌倦而影响接单和拓展。建议你们在忙碌赚钱之余也别忘了从中寻找进一步拓展自身事业的机会和领域，才不枉2017年的好人缘。 "]
     * luckeyStone : 蓝玉髓
     * future :
     */

    private int year;
    private MimaBean mima;
    private String luckeyStone;
    private String future;
    private List<String> career;
    private List<String> love;
    private List<String> health;
    private List<String> finance;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public MimaBean getMima() {
        return mima;
    }

    public void setMima(MimaBean mima) {
        this.mima = mima;
    }

    public String getLuckeyStone() {
        return luckeyStone;
    }

    public void setLuckeyStone(String luckeyStone) {
        this.luckeyStone = luckeyStone;
    }

    public String getFuture() {
        return future;
    }

    public void setFuture(String future) {
        this.future = future;
    }

    public List<String> getCareer() {
        return career;
    }

    public void setCareer(List<String> career) {
        this.career = career;
    }

    public List<String> getLove() {
        return love;
    }

    public void setLove(List<String> love) {
        this.love = love;
    }

    public List<String> getHealth() {
        return health;
    }

    public void setHealth(List<String> health) {
        this.health = health;
    }

    public List<String> getFinance() {
        return finance;
    }

    public void setFinance(List<String> finance) {
        this.finance = finance;
    }

    public static class MimaBean {
        /**
         * info : 放平心态 选择未来
         * text : ["对大多数水瓶座而言，未来一年是不缺风光，却需要自己放平心态，为未来做出选择的一年。命主星土星还将在你们的朋友宫和愿望宫停留几乎整年，你将继续检验和审视自己的朋友圈，抛离\u201c不良资产\u201d，只选择那些与你可以真正\u201c同甘苦共富贵\u201d的朋友共同前进。木天拱持续作用你的人际宫位，也将改变你过去两年一贯深居简出的状态，令当下被无形\u201c禁锢\u201d的状态终于破局，有选择地活跃于各个社交舞台之上，并借助他人之力实现个人价值和水平的提升。对水瓶座来说，难点将不在外界环境好坏（因为已经在2016年形成良性循环），而在于自己可否以坦然心态接受他人的好意，以及明白不切实际的想法必须抛弃才可能更好地出发。木星持续发力也会让你更加明确自己应该选择怎样的方向，抽丝剥茧只留下那些真正适合自己的选择和内涵，进入真正合适自身的领域，尤其利好出版、旅行、考试、出国、高等教育等等行业。"]
         */

        private String info;
        private List<String> text;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public List<String> getText() {
            return text;
        }

        public void setText(List<String> text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return "info='" + info + '\'' +
                    ", text=" + text.get(0);
        }
    }

    @Override
    public String toString() {
        return "YearConstellation{" +
                "year=" + year +
                ", mima=" + mima +
                ", luckeyStone='" + luckeyStone + '\'' +
                ", future='" + future + '\'' +
                ", career=" + career +
                ", love=" + love +
                ", health=" + health +
                ", finance=" + finance +
                '}';
    }
}
