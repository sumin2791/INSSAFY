package com.ssafy.pjt1.model.dto.vote;

public class VoteSelectDto {

    private String user_id;
    private int vote_item_id;
    private int is_used;

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getVote_item_id() {
        return this.vote_item_id;
    }

    public void setVote_item_id(int vote_item_id) {
        this.vote_item_id = vote_item_id;
    }

    public int getIs_used() {
		return this.is_used;
	}

	public void setIs_used(int is_used) {
		this.is_used = is_used;
    }
    
    public VoteSelectDto(){
        super();
    }
}
