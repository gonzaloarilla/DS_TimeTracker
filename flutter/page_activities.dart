import 'dart:async';

import 'package:flutter/material.dart';
import 'package:focused_menu/focused_menu.dart';
import 'package:focused_menu/modals.dart';
import 'package:time_tracker_flutter/page_info.dart';
import 'package:time_tracker_flutter/page_intervals.dart';
import 'package:time_tracker_flutter/page_report.dart';
import 'package:time_tracker_flutter/tree.dart' hide getTree;
import 'package:time_tracker_flutter/requests.dart';
import 'package:time_tracker_flutter/page_menu.dart';


class PageActivities extends StatefulWidget {
  late final int id;
  PageActivities(this.id);

  @override
  _PageActivitiesState createState() => _PageActivitiesState();
}

class _PageActivitiesState extends State<PageActivities> {
  late int id;
  late Future<Tree> futureTree;
  late Tree tree;
  PageReport pageReport = new PageReport();
  var isActive = false;

  late Timer _timer;
  static const int periodeRefresh = 2; // better a multiple of periode in TimeTracker, 2 seconds


  @override
  void initState() {
    super.initState();
    id = widget.id; // of PageActivities
    futureTree = getTree(id);
    _activateTimer();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(
      future: futureTree,
      // this makes the tree of children, when available, go into snapshot.data
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          return Scaffold(
            appBar: AppBar(
              title : _titleText(snapshot),
              //title: Text(snapshot.data!.root.name),
              actions: <Widget>[
                IconButton(icon: Icon(Icons.home),
                    onPressed: () {
                      Navigator.popUntil(context, ModalRoute.withName('/'));
                    } // TODO Gonzalo: comprovar be aixo
                ),
                //TODO other actions
              ],
            ),
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.root.children.length,
              itemBuilder: (BuildContext context, int index) {
                Activity activity = snapshot.data!.root.children[index];
                return FocusedMenuHolder( // Open option menu list
                    child: _buildRow(activity, index),
                    blurSize: 2,
                    blurBackgroundColor: Colors.white,
                    menuWidth: MediaQuery.of(context).size.width*0.5,
                    menuItemExtent: 70,
                    onPressed: (){},
                    menuItems: (activity is Task) ? <FocusedMenuItem>[
                      FocusedMenuItem(
                          title: activity.active ? Text("Stop Task") : Text("Start Task"),
                          onPressed: (){
                            if ((activity as Task).active) {
                              stop(activity.id);
                            } else {
                              start(activity.id);
                            }
                            _refresh();
                          },
                          trailingIcon: (activity as Task).active ? Icon(Icons.pause_outlined) : Icon(Icons.play_arrow_outlined)),
                      FocusedMenuItem(title: Text("Details"), onPressed: () => _onDetailsClick(id), trailingIcon: Icon(Icons.info_outline)),
                      FocusedMenuItem(title: Text("Delete", style: TextStyle(color: Colors.white),), onPressed: (){}, trailingIcon: Icon(Icons.delete, color: Colors.white), backgroundColor: Colors.redAccent),
                    ] : <FocusedMenuItem>[
                      FocusedMenuItem(title: Text("Details"), onPressed: () => _onDetailsClick(id), trailingIcon: Icon(Icons.info_outline)),
                      FocusedMenuItem(title: Text("Delete", style: TextStyle(color: Colors.white),), onPressed: (){}, trailingIcon: Icon(Icons.delete, color: Colors.white), backgroundColor: Colors.redAccent),
                    ]);
              },
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(),
            ),
            floatingActionButton: FloatingActionButton(
              backgroundColor: Colors.blue,
              child: const Icon(Icons.add,),
              onPressed: () {_onMenuClick(snapshot.data!.root.id);},
              tooltip: "Add a new node",
              
            ),
          );
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        // By default, show a progress indicator
        return Container(
            height: MediaQuery.of(context).size.height,
            color: Colors.white,
            child: Center(
              child: CircularProgressIndicator(),
            ));
      },
    );

  }

  Widget _titleText(var snapshot) {
    String text = snapshot.data!.root.name;
    if (snapshot.data!.root.name == "root") {
      text = "TimeTracker";
    }
    return Text(text);
  }

  Widget _buildRow(Activity activity, int index) {
    String strDuration = Duration(seconds: activity.duration).toString().split('.').first;
    // split by '.' and taking first element of resulting list removes the microseconds part
    if (activity is Project) {
      return ListTile(
        title: Text('Project - ${activity.name}'),
        trailing: Text('$strDuration'),
        onTap: () => _navigateDownActivities(activity.id),
      );
    } else if (activity is Task) {
      Task task = activity as Task;
      // at the moment is the same, maybe changes in the future
      Widget trailing;
      trailing = Text('$strDuration');
      return ListTile(
        title: Text('Task - ${activity.name}'),
        trailing: trailing,
        onTap: () => _navigateDownIntervals(activity.id),

      );
    } else {
      throw(Exception("Activity that is neither a Task or a Project"));
      // this solves the problem of return Widget is not nullable because an
      // Exception is also a Widget?
    }
  }

  void _navigateDownActivities(int childId) {
    _timer.cancel();
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => PageActivities(childId),
    )).then((var value) {
      _activateTimer();
      _refresh();
    });
  }

  void _navigateDownIntervals(int childId) {
    _timer.cancel();
    Navigator.of(context)
        .push(MaterialPageRoute<void>(
      builder: (context) => PageIntervals(childId),
    )).then((var value) {
      _activateTimer();
      _refresh();
    });
  }

  void _onReportClick() {
    print("Click");
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageReport()));
  }
  void _onMenuClick(id) {
    print("Add node clicked");
    print("Current node id: " + id.toString());
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageMenu(id)));
  }

  void _onDetailsClick(id) {
    print("Click");
    Navigator.of(context)
        .push(MaterialPageRoute<void>(builder: (context) => PageInfo(id)));
  }

  void _refresh() async {
    futureTree = getTree(id);
    setState(() {});
  }

  void _activateTimer() {
    _timer = Timer.periodic(Duration(seconds: periodeRefresh), (Timer t) {
      futureTree = getTree(id);
      setState(() {});
    });
  }

  @override
  void dispose() {
    // "The framework calls this method when this State object will never build again"
    // therefore when going up
    _timer.cancel();
    super.dispose();
  }


}
