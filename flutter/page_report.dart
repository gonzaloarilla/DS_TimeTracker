import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/tree.dart';
import 'package:intl/intl.dart';


class PageReport extends StatefulWidget {
  @override
  _PageReportState createState() => _PageReportState();
}

class _PageReportState extends State<PageReport> {
  late Tree tree;
  String defaultPeriod = 'Last week';
  String defaultContent = "Brief";

  String defaultFormat = "Web page";
  var itemsPeriod = ['Last week', 'This week', 'Yesterday', 'Today', 'Other'];
  var itemsContent = ['Brief', 'Detailed', 'Statistic'];
  var itemsFormat = ['Web page', 'PDF', 'Text'];

  DateTime pickedStart = DateTime.now();
  DateTime pickedEnd = DateTime.now();

  late var today, yesterday, mondayThisWeek, sundayThisWeek, sundayLastWeek, mondayLastWeek;


  @override
  void initState() {
    super.initState();
    tree = getTree();
    today = DateTime.now();
    yesterday = today.subtract(Duration(days:1));
    mondayThisWeek = DateTime(today.year, today.month, today.day - today.weekday + 1);
    sundayThisWeek = _getSundayThisWeek(today);


    mondayLastWeek = mondayThisWeek.subtract(new Duration(days:7));
    sundayLastWeek = mondayThisWeek.subtract(new Duration(days:1));
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Report"),
      ),
      body: Container(
        margin: EdgeInsets.only(left: 40),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Row(
                children: [
                  Container(
                    child: Text("Period",),
                    width: 120,

                  ),
                  DropdownButton(
                    value: defaultPeriod,
                    icon: Icon(Icons.keyboard_arrow_down),
                    items: itemsPeriod.map((String items) {
                      return DropdownMenuItem(
                          value: items,
                          child: Text(items)
                      );
                    }
                    ).toList(),
                    onChanged: (String? newValue) {
                      setState(() {
                        defaultPeriod = newValue!;
                      });
                      _checkPeriod(context);
                    },
                  ),
                ],
              ),
              Row(
                children: [
                  Container(
                    child: Text("From"),
                    width: 120,

                  ),

                  Text(DateFormat('yyyy-MM-dd').format(pickedStart)),
                  IconButton(
                    color: Colors.blue,
                    icon: Icon(Icons.calendar_today_outlined),
                    onPressed: () {
                      setState(() {});
                      _pickStartDate(context);
                    },
                  ),
                ],
              ),
              Row(
                children: [
                  Container(
                    child: Text("To"),
                    width: 120,

                  ),
                  Text(DateFormat('yyyy-MM-dd').format(pickedEnd)),
                  IconButton(
                    color: Colors.blue,
                    icon: Icon(Icons.calendar_today_outlined),
                    onPressed: () {
                      setState(() {});
                      _pickEndDate(context);
                      _checkRange(context);
                    },
                  ),
                ],

              ),
              Row(
                children: [
                  Container(
                    child: Text("Content"),
                    width: 120,

                  ),
                  DropdownButton(
                    value: defaultContent,
                    icon: Icon(Icons.keyboard_arrow_down),
                    items: itemsContent.map((String items) {
                      return DropdownMenuItem(
                          value: items,
                          child: Text(items)
                      );
                    }
                    ).toList(),
                    onChanged: (String? newValue) {
                      setState(() {
                        defaultContent = newValue!;
                      });
                    },
                  ),
                ],
              ),
              Row(
                children: [
                  Container(
                    child: Text("Format"),
                    width: 120,

                  ),
                  DropdownButton(
                    value: defaultFormat,
                    icon: Icon(Icons.keyboard_arrow_down),
                    items: itemsFormat.map((String items) {
                      return DropdownMenuItem(
                          value: items,
                          child: Text(items)
                      );
                    }
                    ).toList(),
                    onChanged: (String? newValue) {
                      setState(() {
                        defaultFormat = newValue!;
                      });
                    },
                  ),
                ],
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Container(
                      child: TextButton(
                        style: TextButton.styleFrom(
                            textStyle: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.bold)
                        ),
                        onPressed: () {},
                        child: const Text('Generate'),
                      )
                  )
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  void _pickStartDate(BuildContext context) async {
    DateTime? newStart = await showDatePicker(
      context: context,
      firstDate: DateTime(today.year - 5),
      lastDate: DateTime(today.year + 5),
      initialDate: today,
    );
    setState(() {
      pickedStart = newStart!;
      //String formatted = DateFormat('yyyy-MM-dd').format(selectedDate);
      print(pickedStart.toString());
    });
  }

  void _pickEndDate(BuildContext context) async {
    DateTime? newEnd = await showDatePicker(
      context: context,
      firstDate: DateTime(today.year - 5),
      lastDate: DateTime(today.year + 5),
      initialDate: today,
    );
    setState(() {
      pickedEnd = newEnd!;
      //String formatted = DateFormat('yyyy-MM-dd').format(selectedDate);
      print(pickedEnd.toString());
    });
  }


  void _checkPeriod(BuildContext context) {
    print(defaultPeriod);
    switch(defaultPeriod) {
      case "Last week":
        pickedStart = mondayLastWeek;
        pickedEnd = sundayLastWeek;
        break;
      case "This week":
        pickedStart = mondayThisWeek;
        pickedEnd = sundayThisWeek;
        print(pickedEnd);
        break;
      case "Yesterday":
        print(yesterday);
        pickedStart = pickedEnd = yesterday;
        break;
      case "Today":
        pickedStart = pickedEnd = today;
        break;
      case "Other":
        break;
    }
  }

  void _checkRange(BuildContext context) {
    if(pickedEnd.difference(pickedStart) < Duration(days: 0)) {
      print("Greater");
      _rangeDialog();
    }
  }

  DateTime _getSundayThisWeek(DateTime dateTime) {
    return dateTime.add(Duration(days: DateTime.daysPerWeek - dateTime.weekday));
  }

  void _rangeDialog() async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Range dates'),
          content: SingleChildScrollView(
            child: ListBody(
              children: const <Widget>[
                Text('The From date is after the To date\n'),
                Text('Please, select a new date.'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text(
                'ACCEPT',
                style: TextStyle(fontSize: 20),
              ),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }

}
