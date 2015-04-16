package com.mygdx.game.FlowerItems;

import com.mygdx.game.SaveItems.GrowthInfo;

import java.util.Calendar;

/**
 * Class to handle times and growing of things, stores in Growth
 */
public class GrowthHandling {

    public float Growth = 0f; //How much the item has grown so far
    private float previousGrowth = 0f;

    public BloomInfo bloomInfo;

    public float GrowthRate; //growth per irl day

    private Calendar lastChecked = Calendar.getInstance();

    /**
     * Class for handling growth over time
     * @param growthRate Rate the flower will grow per day
     * @param bloomStart Start of bloom
     * @param bloomLength Length of bloom
     */
    public GrowthHandling(float growthRate, float bloomStart, float bloomLength) {
        GrowthRate = growthRate;
        bloomInfo = new BloomInfo(bloomStart, bloomLength);
    }
    public GrowthHandling(GrowthInfo info) {
        GrowthRate = info.growthRate;
        Growth = info.latestGrowth;
        previousGrowth = info.previousGrowth;
        lastChecked = Calendar.getInstance();
        lastChecked.setTimeInMillis(info.lastMilliCheck);

        bloomInfo = new BloomInfo(info.bloomStart, info.bloomLength);
    }

    /**
     * Checks the current time and applies the appropriate growth
     * @return The amount that has been grown
     */
    public float CheckTime() {
        previousGrowth = Growth;
        Calendar calendar = Calendar.getInstance();
        Calendar lastCheck = lastChecked;
        lastChecked = calendar;

        long lastMilli = lastCheck.getTimeInMillis();
        long thisMilli = lastChecked.getTimeInMillis();
        long difference = thisMilli - lastMilli;
        float secondsDifference = (float) difference/1000f;

        float toGrow = GrowthRate*secondsDifference;
        Growth += toGrow;

        bloomInfo.Blooming =
                (Growth >= bloomInfo.bloomStart && Growth <= bloomInfo.bloomStart + bloomInfo.bloomLength); //Ascertains whether it is blooming
        return toGrow;
    }

    public float GetPreviousGrowth() {
        return previousGrowth;
    }

    public long GetLastMilliCheck() {
        return lastChecked.getTimeInMillis();
    }

    public float GetAmountLastBloomed() {
        return bloomInfo.BloomBetweenGrowths(previousGrowth, Growth);
    }

    public class BloomInfo {
        private float bloomStart;
        private float bloomLength;
        private float bloomEnd;

        public boolean Blooming = false;

        public BloomInfo(float BloomStart, float BloomLength) {
            bloomStart = BloomStart;
            bloomLength = BloomLength;
            bloomEnd = bloomStart + bloomLength;
        }
        float BloomBetweenGrowths(float before, float after) {
            boolean beforeWithinBloom = before > bloomStart && before < bloomEnd;
            boolean afterWithinBloom = after > bloomStart && after < bloomEnd;
            boolean afterMoreThanBefore = after > before;

            if(before > bloomEnd || after < bloomStart) {
                return 0f; //no blooming here
            }
            //If it's gotten to this point, there's some blooming going on I believe
            if(beforeWithinBloom && afterWithinBloom) {
                //Entirely within the bloom area
                return Math.abs(after - before);
            }

            if(afterWithinBloom && !beforeWithinBloom) {
                //Only the end within bloom area
                if(afterMoreThanBefore) return after - bloomStart;
                else return bloomEnd - after;
            }

            if(beforeWithinBloom && !afterWithinBloom) {
                if(afterMoreThanBefore) return bloomEnd - before;
                else return before - bloomStart;
            }

            if(!beforeWithinBloom && !afterWithinBloom) { //both outside
                if(afterMoreThanBefore) {
                    if(after > bloomEnd && before < bloomStart) {
                        return bloomLength;
                    }
                    else return 0f;
                }
                else {
                    if(before > bloomEnd && after < bloomStart) {
                        return bloomLength;
                    }
                    else return 0f;
                }
            }
            else return 0f;
        }
        public float GetBloomStart() {
            return bloomStart;
        }
        public float GetBloomLength() {
            return bloomLength;
        }
    }
}
