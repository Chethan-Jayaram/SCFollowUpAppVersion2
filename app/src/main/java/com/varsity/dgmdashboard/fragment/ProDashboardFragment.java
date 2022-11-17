package com.varsity.dgmdashboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.intrusoft.scatter.ChartData;
import com.varsity.dgmdashboard.DGMDashboardApplication;
import com.varsity.dgmdashboard.R;
import com.varsity.dgmdashboard.activity.LeadEntryActivity;
import com.varsity.dgmdashboard.activity.ProDashboardMainActivity;
import com.varsity.dgmdashboard.adapter.AreaListAdapter;
import com.varsity.dgmdashboard.adapter.CallStatusListAdapter;
import com.varsity.dgmdashboard.adapter.ChartCallStatusListAdapter;
import com.varsity.dgmdashboard.adapter.ChartDispostionsListAdapter;
import com.varsity.dgmdashboard.adapter.DispostionsListAdapter;
import com.varsity.dgmdashboard.adapter.ViewPagerAdapter;
import com.varsity.dgmdashboard.databinding.FragmentProDashboardBinding;
import com.varsity.dgmdashboard.model.DGMDashboardResponse;
import com.varsity.dgmdashboard.utils.SnackBar;
import com.varsity.dgmdashboard.viewmodel.DashboardViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProDashboardFragment extends Fragment implements View.OnClickListener {
    private FragmentProDashboardBinding mBinding;
    private View snakBarView;
    private DashboardViewModel dashboardViewModel;
    private ArrayList<String> campaignList = new ArrayList<>();
    private ViewPagerAdapter viewPagerAdapter;
    private boolean isCallStatus = true;

    public static ProDashboardFragment getNewInstance(Context context) {
        ProDashboardFragment fragment = new ProDashboardFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pro_dashboard, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        snakBarView = getActivity().findViewById(android.R.id.content);
        dashboardViewModel = new DashboardViewModel(getContext());
        mBinding.setClickListener(this);
        getPRODashboardData();
        mBinding.tvViewDetails.setOnClickListener(this);

        mBinding.tvCallStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCallStatus();
            }
        });

        mBinding.tvDispositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDisposition();
            }
        });

        mBinding.ivList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setList();
            }
        });
        mBinding.ivChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChart();
            }
        });
    }


    private void setList() {
        mBinding.ivList.setImageResource(R.drawable.ic_list_select);
        mBinding.ivChar.setImageResource(R.drawable.ic_chart_unselect);
        if (isCallStatus) {
            mBinding.rvCallStatus.setVisibility(View.VISIBLE);
            mBinding.rvDespositions.setVisibility(View.GONE);
            mBinding.clCallStatus.setVisibility(View.GONE);
            mBinding.clDespositions.setVisibility(View.GONE);
        } else {
            mBinding.rvCallStatus.setVisibility(View.GONE);
            mBinding.rvDespositions.setVisibility(View.VISIBLE);
            mBinding.clCallStatus.setVisibility(View.GONE);
            mBinding.clDespositions.setVisibility(View.GONE);
        }
    }


    private void setChart() {
        mBinding.ivList.setImageResource(R.drawable.ic_list_unselect);
        mBinding.ivChar.setImageResource(R.drawable.ic_chart_select);
        if (isCallStatus) {
            mBinding.rvCallStatus.setVisibility(View.GONE);
            mBinding.rvDespositions.setVisibility(View.GONE);
            mBinding.clCallStatus.setVisibility(View.VISIBLE);
            mBinding.clDespositions.setVisibility(View.GONE);
        } else {
            mBinding.rvCallStatus.setVisibility(View.GONE);
            mBinding.rvDespositions.setVisibility(View.GONE);
            mBinding.clCallStatus.setVisibility(View.GONE);
            mBinding.clDespositions.setVisibility(View.VISIBLE);
        }
    }


    private void selectCallStatus() {
        isCallStatus = true;
        mBinding.tvCallStatus.setTextColor(getResources().getColor(R.color.menu_selected_text));
        mBinding.tvDispositions.setTextColor(getResources().getColor(R.color.menu_text));
        mBinding.tvCallStatus.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_selected));
        mBinding.tvDispositions.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_unselected));
        mBinding.rvCallStatus.setVisibility(View.VISIBLE);
        mBinding.rvDespositions.setVisibility(View.GONE);

        setList();
    }

    private void selectDisposition() {
        isCallStatus = false;
        mBinding.tvCallStatus.setTextColor(getResources().getColor(R.color.menu_text));
        mBinding.tvDispositions.setTextColor(getResources().getColor(R.color.menu_selected_text));
        mBinding.tvCallStatus.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_unselected));
        mBinding.tvDispositions.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_selected));
        mBinding.rvCallStatus.setVisibility(View.GONE);
        mBinding.rvDespositions.setVisibility(View.VISIBLE);

        setList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddLead: {
                startActivity(new Intent(getContext(), LeadEntryActivity.class));
                break;
            }

            case R.id.tvViewDetails: {
                if (mBinding.llPieChart.getVisibility() == View.VISIBLE) {
                    mBinding.llPieChart.setVisibility(View.GONE);
                    mBinding.tvViewDetails.setText(R.string.view_details);

                } else {
                    mBinding.llPieChart.setVisibility(View.VISIBLE);
                    mBinding.tvViewDetails.setText(R.string.hide_details);
                }
                break;
            }
        }
    }


    private void getPRODashboardData() {
        if (DGMDashboardApplication.getInstance().isNetworkAvailable()) {
            dashboardViewModel.getPRODashboardData(snakBarView).observe(getViewLifecycleOwner(), responseModel -> {
                if (responseModel != null) {
                    if (responseModel.size() != 0) {
                        setCampaignData(responseModel);

                    }
                }
            });
        } else {
            SnackBar.showInternetError(getContext(), snakBarView);
        }
    }


    private void setCampaignData(ArrayList<DGMDashboardResponse> responses) {
        if (responses != null && responses.size() != 0) {
            for (DGMDashboardResponse data : responses) {
                campaignList.add(data.getStudentCampingName());
            }
            mBinding.llCampaign.setVisibility(View.VISIBLE);
            AreaListAdapter areaListAdapter = new AreaListAdapter(getContext(), campaignList);
            mBinding.spCampaign.setAdapter(areaListAdapter);

            mBinding.spCampaign.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != -1) {
                        setTotalLeadCount(responses.get(position).getTotal());
                        setChart(responses.get(position).getDistrictsList());

                        if (responses.get(position).getDistrictsList().get(0).getCallStatusList() != null && responses.get(position).getDistrictsList().get(0).getCallStatusList().size() != 0) {
                            setCallStatusData(responses.get(position).getDistrictsList().get(0).getCallStatusList());
                        }

                        if (responses.get(position).getDistrictsList().get(0).getDispostionsList() != null && responses.get(position).getDistrictsList().get(0).getDispostionsList().size() != 0) {
                            setDispostionsList(responses.get(position).getDistrictsList().get(0).getDispostionsList());
                        }
                        selectCallStatus();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            mBinding.llCampaign.setVisibility(View.GONE);
        }
    }

    private void setTotalLeadCount(DGMDashboardResponse.Total totalData) {
        mBinding.tvPendingCount.setText("" + totalData.getPending());
        mBinding.tvPriority.setText("" + totalData.getTodays());
        int total = totalData.getPending() + totalData.getNotAssigned() + totalData.getAssigned() + totalData.getCommunicated() + totalData.getJoined() + totalData.getTodays() + totalData.getNotCommunicated();
        int totalFollowUpCount = totalData.getCommunicated() + totalData.getJoined() + totalData.getNotCommunicated();
        mBinding.tvTotalLeadsCount.setText("" + total);
        mBinding.tvFollowupCount.setText("" + totalFollowUpCount);
    }

    private void setChart(ArrayList<DGMDashboardResponse.Districts> responses) {
        List<ChartData> data1 = new ArrayList<>();
        if (responses.get(0).getPendingPer() != null && responses.get(0).getPendingPer() != 0.0) {
            float per = responses.get(0).getPendingPer().floatValue();
            data1.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, Color.parseColor(responses.get(0).getPendingColorCode())));
            mBinding.llPendingStatus.setVisibility(View.VISIBLE);

            String str = "Pending" + " " + String.format("%.2f", per) + "%";
            mBinding.tvPendingStatus.setText(str);
            mBinding.ivPendingStatus.setBackgroundColor(Color.parseColor(responses.get(0).getPendingColorCode()));
        } else {
            mBinding.llPendingStatus.setVisibility(View.GONE);
        }

        if (responses.get(0).getAssignedPer() != null && responses.get(0).getAssignedPer() != 0.0) {
            float per = responses.get(0).getAssignedPer().floatValue();
            data1.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, Color.parseColor(responses.get(0).getAssignedColorCode())));
            mBinding.llAssignedStatus.setVisibility(View.VISIBLE);

            String str = "Assigned" + " " + String.format("%.2f", per) + "%";
            mBinding.tvAssignedStatus.setText(str);

            mBinding.ivAssignedStatus.setBackgroundColor(Color.parseColor(responses.get(0).getAssignedColorCode()));
        } else {
            mBinding.llAssignedStatus.setVisibility(View.GONE);
        }

        if (responses.get(0).getNotAssignedPer() != null && responses.get(0).getNotAssignedPer() != 0.0) {
            float per = responses.get(0).getNotAssignedPer().floatValue();
            data1.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, Color.parseColor(responses.get(0).getNotAssignedColorCode())));
            mBinding.llNotAssignedStatus.setVisibility(View.VISIBLE);

            String str = "Not Assigned" + " " + String.format("%.2f", per) + "%";
            mBinding.tvNotAssignedStatus.setText(str);

            mBinding.ivNotAssignedStatus.setBackgroundColor(Color.parseColor(responses.get(0).getNotAssignedColorCode()));
        } else {
            mBinding.llNotAssignedStatus.setVisibility(View.GONE);
        }

        if (responses.get(0).getCommunicatedPer() != null && responses.get(0).getCommunicatedPer() != 0.0) {
            float per = responses.get(0).getCommunicatedPer().floatValue();
            data1.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, Color.parseColor(responses.get(0).getCommunicatedColorCode())));
            mBinding.llCommunicatedStatus.setVisibility(View.VISIBLE);

            String str = "Communicated" + " " + String.format("%.2f", per) + "%";
            mBinding.tvCommunicatedStatus.setText(str);

            mBinding.ivCommunicatedStatus.setBackgroundColor(Color.parseColor(responses.get(0).getCommunicatedColorCode()));
        } else {
            mBinding.llCommunicatedStatus.setVisibility(View.GONE);
        }

        if (responses.get(0).getNotCommunicatedPer() != null && responses.get(0).getNotCommunicatedPer() != 0.0) {
            float per = responses.get(0).getNotCommunicatedPer().floatValue();
            data1.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, Color.parseColor(responses.get(0).getNotCommunicatedColorCode())));
            mBinding.llNotCommunicatedStatus.setVisibility(View.VISIBLE);

            String str = "Not Communicated" + " " + String.format("%.2f", per) + "%";
            mBinding.tvNotCommunicatedStatus.setText(str);

            mBinding.ivNotCommunicatedStatus.setBackgroundColor(Color.parseColor(responses.get(0).getNotCommunicatedColorCode()));
        } else {
            mBinding.llNotCommunicatedStatus.setVisibility(View.GONE);
        }

        if (responses.get(0).getJoinedPer() != null && responses.get(0).getJoinedPer() != 0.0) {
            float per = responses.get(0).getJoinedPer().floatValue();
            data1.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, Color.parseColor(responses.get(0).getJoinedColorCode())));
            mBinding.llJoinedStatus.setVisibility(View.VISIBLE);

            String str = "Joined" + " " + String.format("%.2f", per) + "%";
            mBinding.tvJoinedStatus.setText(str);

            mBinding.ivJoinedStatus.setBackgroundColor(Color.parseColor(responses.get(0).getJoinedColorCode()));
        } else {
            mBinding.llJoinedStatus.setVisibility(View.GONE);
        }

        if (responses.get(0).getTodaysPer() != null && responses.get(0).getTodaysPer() != 0.0) {
            float per = responses.get(0).getTodaysPer().floatValue();
            data1.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, Color.parseColor(responses.get(0).getTodaysColorCode())));
            mBinding.llTodayStatus.setVisibility(View.VISIBLE);

            String str = "Todays" + " " + String.format("%.2f", per) + "%";
            mBinding.tvTodaysStatus.setText(str);

            mBinding.ivTodaysStatus.setBackgroundColor(Color.parseColor(responses.get(0).getTodaysColorCode()));
        } else {
            mBinding.llTodayStatus.setVisibility(View.GONE);
        }

        if (data1 != null && data1.size() > 0) {
            //mBinding.llPieChart.setVisibility(View.VISIBLE);
            mBinding.tvViewDetails.setVisibility(View.VISIBLE);
            mBinding.pieChart.setCenterCircleColor(Color.WHITE);
            mBinding.pieChart.setChartData(data1);
            mBinding.pieChart.partitionWithPercent(true);
        } else {
            mBinding.llPieChart.setVisibility(View.GONE);
            mBinding.tvViewDetails.setVisibility(View.GONE);

        }

    }

    private void setCallStatusData(ArrayList<DGMDashboardResponse.CallStatus> responses) {
        CallStatusListAdapter callStatusListAdapter = new CallStatusListAdapter(responses);
        mBinding.rvCallStatus.setAdapter(callStatusListAdapter);
        mBinding.rvCallStatus.setVisibility(View.VISIBLE);

        ChartCallStatusListAdapter chartCallStatusListAdapter = new ChartCallStatusListAdapter(responses);
        mBinding.rvChartCallStatus.setAdapter(chartCallStatusListAdapter);
        setCallStatusChartData(responses);
    }

    private void setDispostionsList(ArrayList<DGMDashboardResponse.Dispostions> responses) {
        DispostionsListAdapter dispostionsListAdapter = new DispostionsListAdapter(responses);
        mBinding.rvDespositions.setAdapter(dispostionsListAdapter);
        mBinding.rvDespositions.setVisibility(View.GONE);

        ChartDispostionsListAdapter chartDispostionsListAdapter = new ChartDispostionsListAdapter(responses);
        mBinding.rvChartDespositions.setAdapter(chartDispostionsListAdapter);
        setDispostionsChartData(responses);
    }


    private void setCallStatusChartData(ArrayList<DGMDashboardResponse.CallStatus> responses) {
        if (responses != null && responses.size() != 0) {
            List<ChartData> pieChartData = new ArrayList<>();
            for (DGMDashboardResponse.CallStatus data : responses) {
                if (data.getPercentage() != null && data.getPercentage() != 0.0) {

                    float per = data.getPercentage().floatValue();
                    int color = Color.parseColor(data.getColourCode().trim());
                    pieChartData.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, color));
                }
            }

            if (pieChartData != null && pieChartData.size() != 0) {
                mBinding.pieChartCallStatus.setVisibility(View.VISIBLE);
                mBinding.pieChartCallStatus.setCenterCircleColor(Color.WHITE);
                mBinding.pieChartCallStatus.setChartData(pieChartData);
                mBinding.pieChartCallStatus.partitionWithPercent(true);
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.roboto_light);
                mBinding.pieChartDespositions.setTextTypeFace(typeface);
            } else {
                mBinding.pieChartCallStatus.setVisibility(View.GONE);
            }
        } else {
            mBinding.pieChartCallStatus.setVisibility(View.GONE);
        }
    }


    private void setDispostionsChartData(ArrayList<DGMDashboardResponse.Dispostions> responses) {
        if (responses != null && responses.size() != 0) {
            List<ChartData> pieChartData = new ArrayList<>();
            for (DGMDashboardResponse.Dispostions data : responses) {
                if (data.getPercentage() != null && data.getPercentage() != 0.0) {
                    float per = data.getPercentage().floatValue();
                    int color = Color.parseColor(data.getColourCode().trim());
                    pieChartData.add(new ChartData("" + String.format("%.2f", per) + "%", per, Color.WHITE, color));
                }
            }

            if (pieChartData != null && pieChartData.size() != 0) {
                mBinding.pieChartDespositions.setVisibility(View.VISIBLE);
                mBinding.pieChartDespositions.setCenterCircleColor(Color.WHITE);
                mBinding.pieChartDespositions.setChartData(pieChartData);
                mBinding.pieChartDespositions.partitionWithPercent(true);
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.roboto_light);
                mBinding.pieChartDespositions.setTextTypeFace(typeface);
            } else {
                mBinding.pieChartDespositions.setVisibility(View.GONE);
            }
        } else {
            mBinding.pieChartDespositions.setVisibility(View.GONE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ProDashboardMainActivity.updateCount();
    }

}
