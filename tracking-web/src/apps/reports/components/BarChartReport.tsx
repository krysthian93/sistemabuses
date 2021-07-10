/* eslint-disable react/forbid-prop-types */
import React from 'react';
import {
    Bar,
    BarChart,
    CartesianGrid,
    Legend,
    Tooltip,
    XAxis,
    YAxis,
} from 'recharts';
import PropTypes from 'prop-types';

export default function BarChartReport({ data, nameBar, dataKey, color }) {
    return (
        <BarChart
            width={800}
            height={510}
            data={data || []}
            margin={{
                top: 5, right: 30, left: 20, bottom: 5,
            }}
        >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar name={nameBar} dataKey={dataKey} fill={color} barSize={20} />
        </BarChart>
    );
}

BarChartReport.propTypes = {
    data: PropTypes.array.isRequired,
    nameBar: PropTypes.string.isRequired,
    dataKey: PropTypes.string.isRequired,
    color: PropTypes.string.isRequired,
};
