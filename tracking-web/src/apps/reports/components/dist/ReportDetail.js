"use strict";
exports.__esModule = true;
/* eslint-disable max-len */
var react_1 = require("react");
var core_1 = require("@material-ui/core");
var styles_1 = require("@material-ui/core/styles");
var pickers_1 = require("@material-ui/pickers");
var prop_types_1 = require("prop-types");
var moment = require("moment");
var Api_1 = require("../../../utils/Api");
var BarChartReport_1 = require("./BarChartReport");
var TypeReportEnum_1 = require("../../../utils/TypeReportEnum");
var TypeReportColorEnum_1 = require("../../../utils/TypeReportColorEnum");
var useStyles = styles_1.makeStyles(function (theme) { return ({
    margin: {
        margin: theme.spacing(1)
    }
}); });
var BootstrapInput = styles_1.withStyles(function (theme) { return ({
    root: {
        'label + &': {
            marginTop: theme.spacing(3)
        }
    },
    input: {
        borderRadius: 4,
        position: 'relative',
        backgroundColor: theme.palette.background.paper,
        border: '1px solid #ced4da',
        fontSize: 16,
        padding: '10px 26px 10px 12px',
        transition: theme.transitions.create(['border-color', 'box-shadow']),
        // Use the system font instead of the default Roboto font.
        fontFamily: [
            '-apple-system',
            'BlinkMacSystemFont',
            '"Segoe UI"',
            'Roboto',
            '"Helvetica Neue"',
            'Arial',
            'sans-serif',
            '"Apple Color Emoji"',
            '"Segoe UI Emoji"',
            '"Segoe UI Symbol"',
        ].join(','),
        '&:focus': {
            borderRadius: 4,
            borderColor: '#80bdff',
            boxShadow: '0 0 0 0.2rem rgba(0,123,255,.25)'
        }
    }
}); })(core_1.InputBase);
var getChartDescription = function (name) {
    var _a;
    return (_a = {},
        _a[TypeReportEnum_1["default"].DISTANCE_BETWEEN] = 'Distancia Recorrida',
        _a[TypeReportEnum_1["default"].TOUR_PER_MONTH] = 'Viajes por mes',
        _a[TypeReportEnum_1["default"].TIME_TRAVELED] = 'Tiempo viajado',
        _a)[name];
};
function ReportDetail(props) {
    var _a = react_1["default"].useState(TypeReportEnum_1["default"].DISTANCE_BETWEEN), typeReport = _a[0], setTypeReport = _a[1];
    var _b = react_1["default"].useState(new Date()), year = _b[0], setYear = _b[1];
    var _c = react_1["default"].useState({
        data: [],
        nameBar: '',
        dataKey: '',
        color: ''
    }), dataReport = _c[0], setDataReport = _c[1];
    var classes = useStyles();
    var getReport = function (year, typeReport, color) {
        if (color === void 0) { color = '#66ccff'; }
        Api_1["default"].get(process.env.REACT_APP_BASE_URL.concat('/reports/', typeReport), { params: { year: year } })
            .then(function (response) {
            setDataReport({
                data: response.data
                    .map(function (_a) {
                    var _b;
                    var _c = typeReport, value = _a[_c], month = _a.month;
                    return (_b = { name: moment.months(month) }, _b[typeReport] = value, _b);
                }),
                nameBar: getChartDescription(typeReport),
                dataKey: typeReport,
                color: color
            });
        })["catch"](function (error) {
            console.log('error: ', error);
        });
    };
    react_1.useEffect(function () {
        var _a, _b, _c, _d, _e, _f;
        setTypeReport(((_b = (_a = props === null || props === void 0 ? void 0 : props.location) === null || _a === void 0 ? void 0 : _a.state) === null || _b === void 0 ? void 0 : _b.typeReport) || TypeReportEnum_1["default"].DISTANCE_BETWEEN);
        getReport(year.getFullYear(), ((_d = (_c = props === null || props === void 0 ? void 0 : props.location) === null || _c === void 0 ? void 0 : _c.state) === null || _d === void 0 ? void 0 : _d.typeReport) || TypeReportEnum_1["default"].DISTANCE_BETWEEN, ((_f = (_e = props === null || props === void 0 ? void 0 : props.location) === null || _e === void 0 ? void 0 : _e.state) === null || _f === void 0 ? void 0 : _f.color) || TypeReportColorEnum_1["default"].DISTANCE_BETWEEN);
    }, []);
    var handleChangeReport = function (e) {
        setTypeReport(e.target.value);
        getReport(year.getFullYear(), e.target.value);
    };
    var handleChangeYear = function (e) {
        setYear(new Date(e));
        getReport(new Date(e).getFullYear(), typeReport);
    };
    return (react_1["default"].createElement(core_1.Grid, { container: true, direction: "column" },
        react_1["default"].createElement(core_1.Grid, { item: true, container: true, direction: "row" },
            react_1["default"].createElement(core_1.Grid, { item: true, xs: 12, lg: 4 },
                react_1["default"].createElement(core_1.FormControl, { className: classes.margin },
                    react_1["default"].createElement(core_1.InputLabel, { id: "demo-customized-select-label" }, "Tipo de Reporte"),
                    react_1["default"].createElement(core_1.Select, { labelId: "demo-customized-select-label", id: "demo-customized-select", value: typeReport, onChange: handleChangeReport, input: react_1["default"].createElement(BootstrapInput, null) },
                        react_1["default"].createElement(core_1.MenuItem, { value: TypeReportEnum_1["default"].DISTANCE_BETWEEN }, "Distancia Recorrida"),
                        react_1["default"].createElement(core_1.MenuItem, { value: TypeReportEnum_1["default"].TOUR_PER_MONTH }, "Viajes por mes"),
                        react_1["default"].createElement(core_1.MenuItem, { value: TypeReportEnum_1["default"].TIME_TRAVELED }, "Tiempo viajado")))),
            react_1["default"].createElement(core_1.Grid, { item: true, xs: 12, lg: 4, container: true, justify: "flex-start", alignItems: "center" },
                react_1["default"].createElement(pickers_1.DatePicker, { views: ['year'], label: "A\u00F1o", value: year, onChange: handleChangeYear })),
            react_1["default"].createElement(core_1.Grid, { item: true, xs: 12, lg: 4 },
                react_1["default"].createElement("p", null, "1"))),
        react_1["default"].createElement(core_1.Grid, { item: true },
            react_1["default"].createElement(BarChartReport_1["default"], { nameBar: dataReport.nameBar, dataKey: dataReport.dataKey, data: dataReport.data, color: dataReport.color }))));
}
exports["default"] = ReportDetail;
ReportDetail.propTypes = {
    location: prop_types_1["default"].shape({
        state: prop_types_1["default"].shape({
            typeReport: prop_types_1["default"].string,
            color: prop_types_1["default"].string
        })
    }).isRequired
};
