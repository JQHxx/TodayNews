package com.news.today.todaynews.homesys.shanghai.presenter;

import com.news.today.todaynews.base.DaggerMvpFragment;
import com.news.today.todaynews.base.DaggerMvpPresenter;
import com.news.today.todaynews.homesys.shanghai.lf.IShangHaiContract;
import com.news.today.todaynews.homesys.shanghai.view.MostListFragment;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by anson on 2018/5/2.
 */

public class ShangHaiPresenter extends DaggerMvpPresenter<IShangHaiContract.IView> implements IShangHaiContract.IPresenter {

    private ArrayList<DaggerMvpFragment> fragments = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private String dec = "上海，简称“沪”或“申”，是中国共产党的诞生地，中华人民共和国直辖市，国家中心城市，超大城市，沪杭甬大湾区核心城市，国际经济、金融、贸易、航运、科技创新中心 [1-2]  ，首批沿海开放城市。上海地处长江入海口，是长江经济带的龙头城市，隔东中国海与日本九州岛相望，南濒杭州湾，北、西与江苏、浙江两省相接。 [3-5] \n" +
            "春秋战国时期，上海是楚国春申君黄歇的封邑，故别称申。四、五世纪晋朝时期，因渔民创造捕鱼工具“扈”，江流入海处称“渎”，因此松江下游一带称为“扈渎”，以后又改“沪”，故上海简称“沪”。 [6-7]  唐朝置华亭县。上海是国家历史文化名城，拥有深厚的近代城市文化底蕴和众多历史古迹。江浙吴越文化与西方传入的工业文化相融合形成上海特有的海派文化。 [8-9]  1843年后上海成为对外开放的商埠并迅速发展成为远东第一大城市。 [10-11] \n" +
            "上海市总面积6340平方公里，辖16个市辖区，属亚热带湿润季风气候。 [12]  上海GDP居中国城市第一位，亚洲城市第二位，仅次于日本东京。上海是全球著名的金融中心，全球人口规模和面积最大的都会区之一。上海被GaWC评为世界一线城市 [13]  。上海住户存款总额和人均住户存款均居全国第二 [14]  。2017年，上海高新技术企业总数达到7642家。 [15]  福布斯2017年“中国大陆最佳商业城市排行榜”排第一位。 [16] \n" +
            "上海港货物吞吐量和集装箱吞吐量均居世界第一，设有中国大陆首个自贸区中国（上海）自由贸易试验区。上海市与安徽、江苏、浙江共同构成了长江三角洲城市群，是世界六大城市群之一。 [17] 2017年内成功举办第三十四届“上海之春”国际音乐节、第十九届中国上海国际艺术节、第二届上海艾萨克×斯特恩国际小提琴比赛、上海国际电影电视节、上海世博会博物馆开馆、第五届市民文化节等重大文化活动。全年市民参与文化活动人数近3000万人次。继续实施新一轮公共文化从业人员“三年万人培训”项目，年内参训7216人次。大世界非遗中心、世博会博物馆、上海交响音乐博物馆对外开放。至年末，全市有市、区级文化馆、群众艺术馆25个，艺术表演团体210个，市、区级公共图书馆24个，档案馆49个，博物馆125个。全市共有公共广播节目22套，公共电视节目25套。有线电视用户765.43万户，有线数字电视用户697.18万户。全年生产电视剧40部，共1760集；动画电视8160分钟。全年共出版报纸9.13亿份、各类期刊0.94亿册、图书4.23亿册;摄制完成82部影片。";

    @Inject
    public ShangHaiPresenter(IShangHaiContract.IView view) {
        super(view);
    }


    @Override
    protected IShangHaiContract.IView getEmptyView() {
        return IShangHaiContract.emptyView;
    }

    @Override
    public void initFragments() {
        fragments.add(new MostListFragment());
        fragments.add(new MostListFragment());
        fragments.add(new MostListFragment());
        initTitles();
    }

    private void initTitles() {
        titles.add("笑话");
        titles.add("电影");
        titles.add("娱乐");
    }

    @Override
    public String getDec() {
        return dec;
    }

    @Override
    public ArrayList<DaggerMvpFragment> getFragments() {
        return fragments;
    }

    @Override
    public ArrayList<String> getTitles() {
        return titles;
    }


}
