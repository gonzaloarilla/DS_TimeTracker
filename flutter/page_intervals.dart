import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/tree.dart' as Tree hide getTree;
// to avoid collision with an Interval class in another library
import 'package:time_tracker_flutter/requests.dart';

class PageIntervals extends StatefulWidget {
  final int id; // final because StatefulWidget is immutable

  PageIntervals(this.id);

  @override
  _PageIntervalsState createState() => _PageIntervalsState();
}

class _PageIntervalsState extends State<PageIntervals> {
  late int id;
  late Future<Tree.Tree> futureTree;

  @override
  void initState() {
    super.initState();
    id = widget.id;
    futureTree = getTree(id);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree.Tree>(
      future: futureTree,
      // this makes the tree of children, when available, go into snapshot.data
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          int numChildren = snapshot.data!.root.children.length;
          return Scaffold(
            appBar: AppBar(
              title: Text(snapshot.data!.root.name),
              actions: <Widget>[
                IconButton(icon: Icon(Icons.home),
                  onPressed: () {}, // TODO
                )
              ],
            ),
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: numChildren,
              itemBuilder: (BuildContext context, int index) =>
                  _buildRow(snapshot.data!.root.children[index], index),
              separatorBuilder: (BuildContext context, int index) =>
              const Divider(),
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

  Widget _buildRow(Tree.Interval interval, int index) {
    String sDuration = Duration(seconds: interval.duration).toString().split('.').first;
    String sInitialDate = interval.initialDate.toString().split('.')[0];
    String sLastDate = interval.lastDate.toString().split('.')[0];

    TextStyle currentStyle;
    if (interval.active){
      currentStyle = TextStyle(color: Colors.green,);
    }
    else {
      currentStyle = TextStyle(color: Colors.black,);
    }
    return ListTile(
      title: Text('from ${sInitialDate} to ${sLastDate}', style: currentStyle,),
      trailing: Text('$sDuration',style: currentStyle,),
    );
  }
}
