package yoyo.app.android.com.yoyoapp.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Example {

    @SerializedName("leader_traveller")
    @Expose
    private LeaderTraveller leaderTraveller;
    @SerializedName("companion_travellers")
    @Expose
    private List<CompanionTraveller> companionTravellers = null;

    public LeaderTraveller getLeaderTraveller() {
        return leaderTraveller;
    }

    public void setLeaderTraveller(LeaderTraveller leaderTraveller) {
        this.leaderTraveller = leaderTraveller;
    }

    public List<CompanionTraveller> getCompanionTravellers() {
        return companionTravellers;
    }

    public void setCompanionTravellers(List<CompanionTraveller> companionTravellers) {
        this.companionTravellers = companionTravellers;
    }

}