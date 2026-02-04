import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
  Legend,
} from "chart.js";
import { Bar } from "react-chartjs-2";

// Register chart components
ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
  Legend
);

const AttendanceChart = () => {
  const data = {
    labels: ["27 Jan", "28", "29", "30", "31", "01 Feb", "02"],
    datasets: [
      {
        label: "Logged Hours",
        data: [9, 9, 9, 9, null, null, 9],
        backgroundColor: "#0d6efd",
        borderRadius: 4,
      },
      {
        label: "Weekly Offs / Holidays / Leave",
        data: [null, null, null, null, 9, 9, null],
        backgroundColor: "#cfd6e0",
        borderRadius: 4,
      },
    ],
  };

  const options = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      y: {
        beginAtZero: true,
        max: 10,
        ticks: {
          stepSize: 2.5,
        },
        title: {
          display: true,
          text: "Hours",
        },
      },
      x: {
        grid: {
          display: false,
        },
      },
    },
    plugins: {
      legend: {
        position: "bottom",
      },
    },
  };

  return (
    <div style={{ height: "300px" }}>
      <Bar data={data} options={options} />
    </div>
  );
};

export default AttendanceChart;
